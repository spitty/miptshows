/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import org.apache.commons.configuration.ConfigurationException;
import org.ncmipt.miptshows.properties.PropertiesManager;
import java.util.List;
/**
 *
 * @author Vlad
 */
public class TestCommonConfiguration
{

    public void config()
    {
    }

    public static void main(String[] args) throws ConfigurationException
    {
        
//        XMLConfiguration conf = new XMLConfiguration("web/WEB-INF/properties.xml");
//        String driver = conf.getString("field.value");
//        System.out.println(driver);
//        String s = PropertiesManager.getDatabaseURL();
//        System.out.println(s);
//        List<String> list = PropertiesManager.getResourcesShare();
//        for(String str: list){
//            System.out.println(str);
//        }
        double rate = PropertiesManager.getDeletingRate();
        System.out.println(rate);
    }
}
