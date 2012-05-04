/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Vlad
 */
public class SuperPropertiesManager
{

    private static final Log LOG = LogFactory.getLog(SuperPropertiesManager.class);

    public static String showProperty()
    {
        String property = "No prop";
        try
        {
            //            Properties properties = new Properties();
            //            System.out.println("->into showProperty");
            //            properties.loadFromXML(new FileInputStream("properties.xml"));
            //property = properties.getProperty("database");
            property = System.getProperty("miptshows.configuration");
            System.out.println(property);
            
        } catch (Throwable ex)
        {
            LOG.error("ERROR", ex);
        }
        return property;
    }
    public static void main(String[] args)
    {
        System.getProperty("miptshows.configuration");
        Properties p = System.getProperties();
        for(Entry<Object, Object> e : p.entrySet())
        {
            System.out.println(e.getKey() + ":" + e.getValue());
        }
    }
}
