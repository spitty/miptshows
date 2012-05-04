package org.ncmipt.miptshows.properties;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Vlad
 */
public class PropertiesManager
{

    private static final String PATH = System.getProperty("miptshows.configuration");
    private static final Log LOG = LogFactory.getLog(PropertiesManager.class);

    /**
     * 
     * @return 
     */
    public static String getSchedulerExpression()
    {
        String schedulerExpression = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            schedulerExpression = conf.getString("scheduler.expression");
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return schedulerExpression;
    }

    /**
     * 
     * @return 
     */
    public static String getApplicationName()
    {
        String applicationName = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            applicationName = conf.getString("general.applicationName");
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return applicationName;
    }

    /**
     * 
     * @return 
     */
    public static List<String> getResourcesShare()
    {
        List shares = new ArrayList<String>();
        List<String> goodShares = new ArrayList<String>();
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            shares = conf.getList("scanResources.share", shares);

            for (Object share : shares)
            {
                goodShares.add("smb:".concat(share.toString()));
            }
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        if (goodShares.isEmpty())
        {
            LOG.fatal("CONFIGURE SHARES IN FILE properties.xml PROPERLY");
        }
        return goodShares;
    }

    /**
     * 
     * @return 
     */
    public static double getDeletingRate()
    {
        double ttl = 0;
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            ttl = Double.valueOf(conf.getString("database.deletingRate"));
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return ttl;
    }

    /**
     * 
     * @return 
     */
    public static String getJNDIDataSourceName()
    {
        String JNDIDataSourceName = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            JNDIDataSourceName = conf.getString("database.JNDIDataSourceName");
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return JNDIDataSourceName;
    }

    /****future*****/
    /**
     * 
     * @return 
     */
    public static String getSchedulerMinute()
    {
        String schedulerMinute = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            schedulerMinute = conf.getString("scheduler.minute");
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return schedulerMinute;
    }

    /**
     * 
     * @return 
     */
    public static String getSchedulerHour()
    {
        String schedulerHour = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            schedulerHour = conf.getString("scheduler.hour");
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return schedulerHour;
    }

    /**
     * 
     * @return 
     */
    public static String getSchedulerInfo()
    {
        String schedulerInfo = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            schedulerInfo = conf.getString("scheduler.info");
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return schedulerInfo;
    }

    /***********/
    /**
     * @return 
     */
    @Deprecated
    public static String getDatabaseDriver()
    {
        String databaseDriver = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            databaseDriver = conf.getString("database.driver");
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return databaseDriver;
    }

    /**
     * 
     * @return 
     */
    @Deprecated
    public static String getDatabaseUserName()
    {
        String databaseUserName = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            databaseUserName = conf.getString("database.username");
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return databaseUserName;
    }

    /**
     * 
     * @return 
     */
    @Deprecated
    public static String getDatabaseUserPassword()
    {
        String databaseUserPassword = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            databaseUserPassword = conf.getString("database.password");
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return databaseUserPassword;
    }

    /**
     * 
     * @return 
     */
    @Deprecated
    public static String getDatabaseURL()
    {
        String databaseURL = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            databaseURL = conf.getString("database.url");
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return databaseURL;
    }
}
