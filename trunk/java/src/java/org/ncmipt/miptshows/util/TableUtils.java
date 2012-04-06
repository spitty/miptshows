
package org.ncmipt.miptshows.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    public static boolean merge(DBUtils dbUtils)
    {
        if (dbUtils == null)
        {
            throw new IllegalArgumentException("dbUtils cannot be null");
        }

        if ( !dbUtils.execute(
                "MERGE"
              + "INTO files f"
              + "USING temp_data td"
              + "ON (td.file_name = f.file_name)"
              + "WHEN NOT MATCH THEN"
                  + "INSERT"
                      + "(f.file_id, f.file_name, f.file_size)"
                  + "VALUES"
                      + "(file_id_generator.nextval, td.file_name, td.file_size)") )
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Cannot merge files and temp_data");
            }
            return false;
        }

        if ( !dbUtils.execute(
                "MERGE"
                + "INTO folders f"
                + "USING temp_data td"
                + "ON (td.folder_name = f.folder_name)"
                + "WHEN NOT MATCH THEN"
                    + "INSERT"
                        + "(f.folder_id, f.folder_name)"
                    + "VALUES"
                        + "(folder_id_generator.nextval, td.folder_name)") )
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Cannot merge folders and temp_data");
            }
            return false;
        }

        if ( !dbUtils.execute(
                "MERGE"
                + "INTO files2folders f2f"
                + "USING"
                + "("
                    + "SELECT file_id, folder_id"
                    + "FROM files f, folders fol, temp_data td"
                    + "WHERE f.file_name = td.file_name"
                        + "AND fol.folder_name = td.folder_name"
                + ") t"
                + "ON ( t.file_id = f2f.file_id AND t.folder_id = f2f.folder_id )"
                + "WHEN NOT MATCH THEN"
                    + "INSERT"
                        + "(f2f.files_id, f2f.folder_id)"
                    + "VALUES"
                        + "(t.files_id, t.folder_id)") )
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Cannot merge f2f and temp_data");
            }
            return false;
        }

        if ( !dbUtils.execute(  "MERGE"
                        + "INTO servers s"
                        + "USING temp_data td"
                        + "ON (td.server = s.server)"
                        + "WHEN NOT MATCH THEN"
                            + "INSERT"
                                + "(s.server_id, s.server)"
                            + "VALUES"
                                + "(server_id_generator.nextval, td.server)") )
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Cannot merge servers and temp_data");
            }
            return false;
        }

        return true;
    }

    /**
     *
     * @param dbUtils
     * @return
     */
    public static boolean clearTempTable(DBUtils dbUtils)
    {
        return dbUtils.execute("DELETE FROM temp_data");
    }



}

        // sql testing
/*      files
MERGE
    INTO files f
    USING temp_data td
    ON (td.file_name = f.file_name)
    WHEN NOT MATCH THEN
        INSERT
            (f.file_id, f.file_name, f.file_size)
        VALUES
            (file_id_generator.nextval, td.file_name, td.file_size)
*/

/*      folders
MERGE
    INTO folders f
    USING temp_data td
    ON (td.folder_name = f.folder_name)
    WHEN NOT MATCH THEN
        INSERT
            (f.folder_id, f.folder_name)
        VALUES
            (folder_id_generator.nextval, td.folder_name)
*/

/*      f2f
MERGE
    INTO files2folders f2f
    USING
    (
        SELECT file_id, folder_id
        FROM files f, folders fol, temp_data td
        WHERE f.file_name = td.file_name
            AND fol.folder_name = td.folder_name
    ) t
    ON ( t.file_id = f2f.file_id AND t.folder_id = f2f.folder_id )
    WHEN NOT MATCH THEN
        INSERT
            (f2f.files_id, f2f.folder_id)
        VALUES
            (t.files_id, t.folder_id)
*/

/*      servers
MERGE
    INTO servers s
    USING temp_data td
    ON (td.server = s.server)
    WHEN NOT MATCH THEN
        INSERT
            (s.server_id, s.server)
        VALUES
            (server_id_generator.nextval, td.server)
*/