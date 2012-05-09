package org.ncmipt.miptshows.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;

/**
 *
 * @author Vlad, Roman
 */
public class TableUtils
{

    private static final Log LOG = LogFactory.getLog(TableUtils.class);

    /**
     * Merge a temporary table and constant tables
     * @param dbUtils - provide a connection for work
     */
    public static void merge(DBUtils dbUtils)
    {
        if (dbUtils == null)
        {
            throw new IllegalArgumentException("dbUtils cannot be null");
        }

        dbUtils.execute(
                "MERGE "
                + "INTO files f "
                + "USING temp_data td "
                + "ON (td.file_name = f.file_name) "
                + "WHEN NOT MATCHED THEN "
                + "INSERT "
                + "(f.file_id, f.file_name, f.file_size, f.last_change_date) "
                + "VALUES "
                + "(file_id_generator.nextval, td.file_name, td.file_size, sysdate) "
                + "WHEN MATCHED THEN "
                + "UPDATE SET f.last_change_date = sysdate");



        dbUtils.execute(
                "MERGE "
                + "INTO folders f "
                + "USING (SELECT UNIQUE folder_name FROM temp_data) td "
                + "ON (td.folder_name = f.folder_name) "
                + "WHEN NOT MATCHED THEN "
                + "INSERT "
                + "(f.folder_id, f.folder_name) "
                + "VALUES "
                + "(folder_id_generator.nextval, td.folder_name) ");



        dbUtils.execute(
                "MERGE "
                + "INTO files2folders f2f "
                + "USING "
                + "("
                + "SELECT file_id, folder_id "
                + "FROM files f, folders fol, temp_data td "
                + "WHERE f.file_name = td.file_name "
                + "AND fol.folder_name = td.folder_name "
                + ") t "
                + "ON ( t.file_id = f2f.file_id AND t.folder_id = f2f.folder_id ) "
                + "WHEN NOT MATCHED THEN "
                + "INSERT "
                + "(f2f.file_id, f2f.folder_id) "
                + "VALUES "
                + "(t.file_id, t.folder_id)");



        dbUtils.execute("MERGE "
                + "INTO servers s "
                + "USING ( SELECT UNIQUE server_name FROM temp_data) td "
                + "ON (td.server_name = s.server_name ) "
                + "WHEN NOT MATCHED THEN "
                + "INSERT "
                + "(s.server_id, s.server_name ) "
                + "VALUES "
                + "(server_id_generator.nextval, td.server_name )");

    }

    /**
     * Clear a temporary table
     * @param dbUtils - provide a connection for work
     */
    public static void clearTempTable(DBUtils dbUtils)
    {
        dbUtils.execute("DELETE FROM temp_data");
    }

    /**
     * Execute searching pathes in constant tables
     * @param showTitle - the title of the show with a required seria
     * @param ruTitle - a russian-language title
     * @param season - a season which contains a required seria
     * @param episode - a required seria
     * @param dbUtils - provide a connection for work
     * @return list of string containing pathes to required serias
     */
    public static List<String> getPathes(String showTitle, String ruTitle, int season, int episode, DBUtils dbUtils)
    {

        List<String> pathes = new ArrayList<String>();
        String query =
                "SELECT DISTINCT fol.folder_name, f.file_name "
                + "FROM folders fol, files f, files2folders f2f "
                + "WHERE f2f.file_id = f.file_id "
                + "AND f2f.folder_id = fol.folder_id "
                + "AND f.file_name LIKE "
                + "''||regexp_replace(lower( ? ),'[^a-zа-яё0-9]+','%')||'%s'||lpad( ? , 2, '0')||'%e'||lpad( ? , 2, '0')||'%' "
                + "OR f.file_name LIKE "
                + "''||regexp_replace(lower( ? ),'[^a-zа-яё0-9]+','%')||'%'||?||'x'||lpad( ? , 2, '0')||'%' "
                + "OR f.file_name LIKE "
                + "''||regexp_replace(lower( ? ),'[^a-zа-яё0-9]+','%')||'%'||?||''||lpad( ? , 2, '0')||'%' ";

        ResultSet result = null;
        PreparedStatement stat = null;

        try
        {
            stat = dbUtils.getConn().prepareStatement(query);
            stat.setString(1, showTitle);
            stat.setInt(2, season);
            stat.setInt(3, episode);
            stat.setString(4, showTitle);
            stat.setInt(5, season);
            stat.setInt(6, episode);
            stat.setString(7, showTitle);
            stat.setInt(8, season);
            stat.setInt(9, episode);
            result = stat.executeQuery();

        } catch (SQLException ex)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Can't create prepared statement", ex);
            }
        }

        if (result == null)
        {
            try
            {

                result.close();
                stat.close();
            } catch (Throwable th)
            {
                if (LOG.isFatalEnabled())
                {
                    LOG.fatal("result in null", th);
                }
            }
        } else
        {
            try
            {
                while (result.next())
                {
                    pathes.add(result.getString(1) + result.getString(2));
                }
            } catch (SQLException e)
            {
                if (LOG.isErrorEnabled())
                {
                    LOG.error("Cannot execute select for searching pathes", e);
                }
            } finally
            {
                try
                {
                    result.getStatement().close();
                    result.close();
                } catch (SQLException ex)
                {
                    if (LOG.isErrorEnabled())
                    {
                        LOG.error("Can't close resultSet", ex);
                    }
                }
            }
        }
        return pathes;
    }

    /**
     * Delete old files
     * @param dbUtils - provide a connection for work
     * @param rate - the rate of deleting files.
     */
    public static void clearOldFiles(DBUtils dbUtils, double rate)
    {
        String query = "DELETE FROM files "
                + "WHERE sysdate - last_change_date > " + rate;
        dbUtils.execute(query);
    }

    /**
     * Delete empty folders
     * @param dbUtils - provide a connection for work
     */
    public static void clearEmptyFolders(DBUtils dbUtils)
    {
        String query =
                "DELETE FROM folders "
                + "WHERE folder_id NOT IN "
                + "( "
                + "SELECT folder_id "
                + "FROM files2folders "
                + ")";

        dbUtils.execute(query);
    }
}
