package org.ncmipt.miptshows.scheduler;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ncmipt.miptshows.db.Updater;
import org.ncmipt.miptshows.properties.PropertiesManager;

/**
 *
 * @author Vlad
 */
@Singleton
@LocalBean
public class SchedulerBean
{

    private static final Log LOG = LogFactory.getLog(SchedulerBean.class);

    @Schedule(second = "0", minute = "13", hour = "01", persistent = false, info = "START SCANNING")
    public void timer()
    {
        try
        {
            List<String> shares = PropertiesManager.getResourcesShare();
            if (LOG.isInfoEnabled())
            {
                LOG.info("SHEDULER STARTS SCAN SHARES" + shares.get(0));
            }
            for (String share : shares)
            {
                Updater.updateDB(share);
            }

        } catch (Throwable th)
        {
            if (LOG.isFatalEnabled())
            {
                LOG.fatal("CAN'T SCAN SHARES", th);
            }
            return;
        }
        if (LOG.isInfoEnabled())
        {
            LOG.info("SHEDULER FINISHED SACCESSFULLY");
        }
    }
}