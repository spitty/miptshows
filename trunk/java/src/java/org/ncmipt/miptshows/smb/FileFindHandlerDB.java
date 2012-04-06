package org.ncmipt.miptshows.smb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Vlad, Roman
 */
public class FileFindHandlerDB implements FileFindHandler
{

    private static final Log LOG = LogFactory.getLog(FileFindHandlerDB.class);
    private static final int INSERTION_MAX = 100;
    private PreparedStatement pstat;
    private int insertionCounter;

    public PreparedStatement getPstat()
    {
        return pstat;
    }

    /**
     *
     * @param pstat
     */
    public FileFindHandlerDB(PreparedStatement pstat)
    {
        insertionCounter = 0;
        this.pstat = pstat;
    }

    @Override
    public void onFileFound(FileObject file)
    {
        try
        {
            pstat.setString(1, file.getName());
            pstat.setString(2, file.getFolder());
            pstat.setInt(3, file.getSize());
            pstat.setString(4, file.getServer());
            pstat.addBatch();
            
            if (++insertionCounter <= INSERTION_MAX)
            {
                pstat.executeBatch();
            }

        } catch (SQLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }
        }
    }
}
