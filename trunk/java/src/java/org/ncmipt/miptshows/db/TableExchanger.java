
package org.ncmipt.miptshows.db;

import org.ncmipt.miptshows.util.DBUtils;


/**
 *
 * @author Ivanov Roman, 917th group, MIPT
 */
public class TableExchanger
{

    /**
     *
     * @param dbUtils
     */
    public static void merge(DBUtils dbUtils)
    {
        dbUtils.execute(  "MERGE"
                        + "INTO files f"
                        + "USING temp_data td"
                        + "ON (td.file_name = f.file_name)"
                        + "WHEN NOT MATCH THEN"
                            + "INSERT"
                                + "(f.file_id, f.file_name, f.file_size)"
                            + "VALUES"
                                + "(file_id_generator.nextval, td.file_name, td.file_size)");

        dbUtils.execute(  "MERGE"
                        + "INTO folders f"
                        + "USING temp_data td"
                        + "ON (td.folder_name = f.folder_name)"
                        + "WHEN NOT MATCH THEN"
                            + "INSERT"
                                + "(f.folder_id, f.folder_name)"
                            + "VALUES"
                                + "(folder_id_generator.nextval, td.folder_name)");

        dbUtils.execute(  "MERGE"
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
                                + "(t.files_id, t.folder_id)");

        dbUtils.execute(  "MERGE"
                        + "INTO servers s"
                        + "USING temp_data td"
                        + "ON (td.server = s.server)"
                        + "WHEN NOT MATCH THEN"
                            + "INSERT"
                                + "(s.server_id, s.server)"
                            + "VALUES"
                                + "(server_id_generator.nextval, td.server)");

    }

    /**
     *
     * @param dbUtils
     */
    public static void clearTempTable(DBUtils dbUtils)
    {
        dbUtils.execute("DELETE FROM temp_data");
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