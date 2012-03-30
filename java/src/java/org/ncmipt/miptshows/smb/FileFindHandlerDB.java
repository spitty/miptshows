
package org.ncmipt.miptshows.smb;

import org.ncmipt.miptshows.util.DBUtils;

/**
 *
 * @author Vlad, Roman
 */
public class FileFindHandlerDB implements FileFindHandler
{
    private static final int INSERTION_MAX = 1000;

    private DBUtils dbUtils;
    private int insertionCounter = 0;

    /**
     *
     * @param dbUtils
     */
    public FileFindHandlerDB(DBUtils dbUtils)
    {
        this.dbUtils = dbUtils;
        insertionCounter = 0;
    }

    @Override
    public void onFileFound(FileObject file)
    {
        if (insertionCounter < INSERTION_MAX)
        {
            dbUtils.executeInsert(file.getName(), file.getFolder(),
                    file.getSize(), file.getServer());
            insertionCounter++;
        } else {
            dbUtils.flush();
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
        dbUtils.flush();
        insertionCounter = 0;
    }
}
