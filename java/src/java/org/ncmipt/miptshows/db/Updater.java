package org.ncmipt.miptshows.db;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ncmipt.miptshows.util.TableUtils;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ncmipt.miptshows.smb.FileFindHandler;
import org.ncmipt.miptshows.smb.FileFindHandlerDB;
import org.ncmipt.miptshows.smb.JcifsController;
import org.ncmipt.miptshows.util.DBUtils;
import org.ncmipt.miptshows.util.IOTools;

/**
 *
 * @author Ivanov Roman, 917th group, MIPT
 */
public class Updater
{

    private static final Log LOG = LogFactory.getLog(Updater.class);

    /**
     * Keep data base in up-to-date form. Add new items from te directory with
     * name share into DB. Use @see ConnectionManager for the recursive
     * traversal of the tree with handler @see FileFindHandlerDB. Then merge
     * into constant tables and clear the temporary table.
     * @param share - the name of scanned share
     */
    public static void updateDB(String share)
    {
        if (share == null)
        {
            throw new IllegalArgumentException("Server cannot be null");
        }

        DBUtils dbUtils = null;
        PreparedStatement pstat = null;
        FileFindHandler handler;

        try
        {
            LOG.debug("->TRY TO SCAN SHARE");
            SmbFile shareSmb = new SmbFile(share);

            if (!shareSmb.exists())
            {
                if (LOG.isErrorEnabled())
                {
                    LOG.error("Cannot find share " + share.toString());
                }
                return;
            }

            dbUtils = new DBUtils();
            pstat = dbUtils.createStatement();
            handler = new FileFindHandlerDB(pstat);

            // It must fill a temporary table
            JcifsController.scanFolder(shareSmb, handler);
            dbUtils.flush(pstat);

            // It must retransmit the data from a temporary table to conctant tables and clear temptable
            TableUtils.merge(dbUtils);
            TableUtils.clearTempTable(dbUtils);

            if (LOG.isDebugEnabled())
            {
                LOG.debug("SHARE WAS SCANNED Updater");
            }

        } catch (SmbException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }

        } catch (MalformedURLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }

        } finally
        {
            dbUtils.close();
            try
            {
                pstat.close();

            } catch (SQLException ex)
            {
                Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
            }
            IOTools.close(pstat);
            IOTools.close(dbUtils);
        }
    }
}
