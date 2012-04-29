
package org.ncmipt.miptshows.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;

/**
 *
 * @author Ivanov Roman, 917th group, MIPT
 */
public class TableUtils
{
    private static final Log LOG = LogFactory.getLog(TableUtils.class);

    /**
     *
     * @param dbUtils
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



        dbUtils.execute(  "MERGE "
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
     *
     * @param dbUtils
     */
    public static void clearTempTable(DBUtils dbUtils)
    {
        dbUtils.execute("DELETE FROM temp_data");
    }

    /**
     *
     * @param showTitle
     * @param ruTitle
     * @param season
     * @param episode
     * @return
     */
    public static List<String> getPathes(String showTitle, String ruTitle, int season, int episode)
    {
        List<String> pathes = new ArrayList<String>();
        String query =
                  "SELECT folder_name "
                + "FROM folders "
                + "WHERE folder_id IN "
                + "("
                    + "SELECT folder_id "
                    + "FROM files2folders "
                    + "WHERE file_id IN "
                    + "("
                        + "SELECT file_id "
                        + "FROM files "
                        + "WHERE file_name LIKE 'Tractor%' "
                    + ") "
                + ")";

        DBUtils dbUtils = new DBUtils();
        ResultSet result = dbUtils.executeQuery(query);
        try
        {
            while(result.next())
            {
                System.out.println("HELLO!");
                pathes.add(result.getString(1));
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
                dbUtils.close();
            } catch (SQLException e)
            {
                if (LOG.isErrorEnabled())
                {
                    LOG.error("Cannot merge servers and temp_data", e);
                }
            }
        }

        return pathes;
    }

    /**
     *
     * @param dbUtils
     * @param rate - the rate of deleting files.
     */
    public static void clearOldFiles(DBUtils dbUtils, int rate)
    {
        String query = "DELETE FROM files "
                     + "WHERE sysdate - last_change_date > " + rate;
        dbUtils.execute(query);
    }

    /**
     *
     * @param dbUtils
     */
    public static void clearEmptyFolders(DBUtils dbUtils)
    {
        String query =
                "SELECT count(*), folder_id "
              + "FROM files2folders "
              + "WHERE folder_id IN "
              + "( "
                  + "SELECT folder_id "
                  + "FROM folders "
              + ")";

        ResultSet result = dbUtils.executeQuery(query);
        try
        {
            while(result.next())
            {
                if (result.getInt(1) == 0)
                {
                    query = "DELETE FROM folders "
                          + "WHERE folder_id = " + result.getInt(2);
                    dbUtils.execute(query);
                }
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(TableUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}