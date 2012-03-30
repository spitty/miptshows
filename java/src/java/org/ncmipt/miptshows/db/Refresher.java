package org.ncmipt.miptshows.db;

import java.net.MalformedURLException;
import jcifs.smb.SmbException;
import org.apache.log4j.Logger;
import jcifs.smb.SmbFile;
import org.apache.log4j.PropertyConfigurator;
import org.ncmipt.miptshows.smb.FileFindHandlerDB;
import org.ncmipt.miptshows.smb.JcifsController;
import org.ncmipt.miptshows.util.DBUtils;

/**
 *
 * @author Ivanov Roman, 917th group, MIPT
 */
public class Refresher
{

    private static final Logger LOG = Logger.getLogger(Refresher.class);

    /**
     * 
     * @param server
     */
    public void uprateDB(final String server)
    {
        if (server == null)
        {
            throw new IllegalArgumentException("Server cannot be null");
        }

        PropertyConfigurator.configure("log4j.properties.txt");

        try
        {
            final SmbFile serverSmb = new SmbFile(server);
            if (!serverSmb.exists())
            {
                LOG.error("Cannot find server " + server);
                return;
            }

            DBUtils dbUtils = new DBUtils();
            dbUtils.connect();
            dbUtils.createStatement();

            // It must fill a temporary table
            FileFindHandlerDB handler = new FileFindHandlerDB(dbUtils);
            JcifsController.scanFolder(serverSmb, handler);
            handler.endInsert();

            // It must retransmit the data from a temporary table to conctant tables and clear temptable
            TableExchanger.merge(dbUtils);
            TableExchanger.clearTempTable(dbUtils);

        } catch (SmbException ex)
        {
            LOG.error("Smth bad", ex);
        } catch (MalformedURLException ex)
        {
            LOG.error("Cannot create SmbFile instance", ex);
        }
    }
}
