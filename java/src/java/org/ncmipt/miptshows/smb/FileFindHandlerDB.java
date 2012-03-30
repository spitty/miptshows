
package org.ncmipt.miptshows.smb;

import org.ncmipt.miptshows.util.DBUtils;

/**
 *
 * @author Vlad, Roman
 */
public class FileFindHandlerDB implements FileFindHandler
{
    private static final int INSERTION_MAX = 100;

    private DBUtils dbutils;
    private int insertionCounter;

    /**
     *
     */
    public FileFindHandlerDB()
    {
        dbutils = new DBUtils();
        dbutils.connect();
        dbutils.createStatement();
        insertionCounter = 0;
    }

    @Override
    public void onFileFound(FileObject file)
    {
        if (insertionCounter < INSERTION_MAX)
        {
            dbutils.executeInsert(file.getName(), file.getFolder(),
                    file.getSize(), file.getServer());
            insertionCounter++;
        } else {
            dbutils.executeInsert();
            insertionCounter = 0;
        }
    }

    /**
     * !!!
     * How should I know, when last insertion? It is temporary method for
     * solution of this problem. Max, pay attention to this.
     */
    public void endInsert()
    {
        dbutils.executeInsert();
        insertionCounter = 0;
    }
}
