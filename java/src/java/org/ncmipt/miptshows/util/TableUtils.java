
package org.ncmipt.miptshows.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
     * @return
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
                      + "(f.file_id, f.file_name, f.file_size) "
                  + "VALUES "
                      + "(file_id_generator.nextval, td.file_name, td.file_size) ");
        
                

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
     * @return
     */
    public static void clearTempTable(DBUtils dbUtils)
    {
        dbUtils.execute("DELETE FROM temp_data");
    }
    
    /**
     *
     * @param name
     * @param season
     * @param episode
     * @return
     */
    public static String getPathes(String showTitle, int season, int episode)
    {
       String path = "";
        String query =
                  "select folder_name "
                + "from folders "
                + "where folder_id in "
                + "("
                    + "select folder_id "
                    + "from files2folders "
                    + "where file_id in "
                    + "("
                        + "select file_id "
                        + "from files "
                        + "where file_name like '" + showTitle + "%"+ season + "%" +episode +"%' "
                    + ") "
                + ")";

        DBUtils dbUtils = new DBUtils();
        ResultSet result = dbUtils.executeQuery(query);
        try
        {
            if (result.next())
            {
                path = result.getString(1);
            }
        } catch (SQLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Cannot merge servers and temp_data", e);
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

        return path;
    }

}