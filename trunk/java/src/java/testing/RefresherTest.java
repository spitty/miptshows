
package testing;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import jcifs.smb.SmbException;

import jcifs.smb.SmbFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ncmipt.miptshows.db.Updater;
import org.ncmipt.miptshows.properties.PropertiesManager;
import org.ncmipt.miptshows.smb.FileFindHandler;
import org.ncmipt.miptshows.smb.FileFindHandlerDB;
import org.ncmipt.miptshows.smb.JcifsController;
import org.ncmipt.miptshows.util.DBUtils;
import org.ncmipt.miptshows.util.IOTools;

import org.ncmipt.miptshows.db.Updater;

import org.ncmipt.miptshows.util.TableUtils;

/** 
 *
 * @author Vlad
 */
public class RefresherTest
{

    public static void main(String[] args) throws SQLException, MalformedURLException, SmbException, NamingException
    {
        

//        DBUtils dbUtils = null;
//        PreparedStatement pstat = null;
//        FileFindHandler handler;
//        
//        
//        dbUtils = new DBUtils();
//        
//        pstat = dbUtils.createStatement();
//        
//        handler = new FileFindHandlerDB(pstat);
        
        // It must fill a temporary table
//        JcifsController.scanFolder(shareSmb, handler);
//        dbUtils.flush(pstat);
        
//        IOTools.close(pstat);
//        IOTools.close(dbUtils);

//        String share = "smb://natalie.campus/incoming/! For/ForMiptShows/MiptShowsTest/";//"smb://natalie.campus/incoming/! For/ForMiptShows/MiptShowsTest/";
//        Updater.updateDB(share);
//        List<String> pathes = TableUtils.getPathes("Tractor Man", "ru", 1, 2);



        // It must retransmit the data from a temporary table to conctant tables and clear temptable
        //TableUtils.merge(dbUtils);
        //TableUtils.clearTempTable(dbUtils);

//        System.out.println(pathes.size());
//        for(String path : pathes)
//        {
//            System.out.println(path);
//        }



        String share = /*"smb://natalie.campus/incoming/! For/917/";//*/"smb://natalie.campus/incoming/! For/ForMiptShows/MiptShowsTest/";
        Updater.updateDB(share);

//        DBUtils dbUtils = new DBUtils();
//        TableUtils.clearEmptyFolders(dbUtils);
//        
//        TableUtils.clearOldFiles(dbUtils, 0);
//        dbUtils.close();


//        List<String> listic = TableUtils.getPathes("Tractor Man", "ru", 1, 2);
//        
//        for(String path : listic)
//        {
//            System.out.println(path);
//        }
//        List<String> pathes = TableUtils.getPathes("piz", "dec", 1, 1);
//        System.out.println(pathes.get(0));
//        for (String path : pathes)
//        {
//            System.out.println(path);
//        }
//        DBUtils dbUlils = new DBUtils();
//        dbUlils.execute("select * from dual");
//        InitialContext ctx = new InitialContext();
//        DataSource datasource = (DataSource) ctx.lookup("jdbc/miptshows");
//        Statement st = datasource.getConnection().createStatement();
        
        System.out.println(PropertiesManager.getJNDIDataSourceName());

    }
}
