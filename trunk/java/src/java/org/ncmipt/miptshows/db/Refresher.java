package org.ncmipt.miptshows.db;

import org.ncmipt.miptshows.util.TableUtils;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.ncmipt.miptshows.smb.FileFindHandler;
import org.ncmipt.miptshows.smb.FileFindHandlerDB;
import org.ncmipt.miptshows.smb.JcifsController;
import org.ncmipt.miptshows.util.DBUtils;
import org.ncmipt.miptshows.util.IOTools;

/**
 *
 * @author Ivanov Roman, 917th group, MIPT
 */
public class Refresher
{

    private static final Log LOG = LogFactory.getLog(Refresher.class);

    /**
     *
     * @param share
     */
    public static void updateDB(final String share)
    {
        if (share == null)
        {
            throw new IllegalArgumentException("Server cannot be null");
        }

        PropertyConfigurator.configure("log4j.properties.txt");

        DBUtils dbUtils = null;
        PreparedStatement pstat = null;
        FileFindHandler handler;

        try
        {
            final SmbFile shareSmb = new SmbFile(share);
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
            // TODO google: try with resources
            IOTools.close(pstat);
            IOTools.close(dbUtils);
        }
    }
    
    
}
