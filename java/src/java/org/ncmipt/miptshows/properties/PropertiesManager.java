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

    private static final String PATH = "web/properties/properties.xml";
    private static final Log LOG = LogFactory.getLog(PropertiesManager.class);

    /**
     * 
     * @return 
     */
    public static String getDatabaseDriver()
    {
        String databaseDriver = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            databaseDriver = conf.getString("database.driver");
            return databaseDriver;
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
    public static String getDatabaseUserName()
    {
        String databaseUserName = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            databaseUserName = conf.getString("database.username");
            return databaseUserName;
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
    public static String getDatabaseUserPassword()
    {
        String databaseUserPassword = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            databaseUserPassword = conf.getString("database.password");
            return databaseUserPassword;
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
    public static String getDatabaseURL()
    {
        String databaseURL = "";
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            databaseURL = conf.getString("database.url");
            return databaseURL;
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return databaseURL;
    }

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
            return schedulerExpression;
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
            return applicationName;
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
        try
        {
            XMLConfiguration conf = new XMLConfiguration(PATH);
            shares = conf.getList("scanResources.share", shares);
            return shares;
        } catch (ConfigurationException ex)
        {
            LOG.error("Can't read properties", ex);
        }
        return shares;
    }
}
