/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ncmipt.miptshows.db.Refresher;
import org.ncmipt.miptshows.smb.FileFindHandler;
import org.ncmipt.miptshows.smb.FileFindHandlerDB;
import org.ncmipt.miptshows.smb.JcifsController;
import org.ncmipt.miptshows.util.DBUtils;
import org.ncmipt.miptshows.util.IOTools;
import org.ncmipt.miptshows.util.TableUtils;

/**
 *
 * @author Vlad
 */
public class RefresherTest
{

    public static void main(String[] args) throws SQLException, MalformedURLException, SmbException
    {/*
        DBUtils dbu = new DBUtils();
        Connection conn  = dbu.getConn();
        Statement stat = conn.createStatement();
        TableUtils.merge(dbu);
        String share = "smb://natalie.campus/incoming/! For/ForMiptShows/MiptShowsTest/";
        final SmbFile shareSmb = new SmbFile(share);
        //System.out.println(shareSmb.exists());

        DBUtils dbUtils = null;
        PreparedStatement pstat = null;
        FileFindHandler handler;
        
            
        dbUtils = new DBUtils();
     
        pstat = dbUtils.createStatement();
    
        handler = new FileFindHandlerDB(pstat);
   
     
        // It must fill a temporary table
        JcifsController.scanFolder(shareSmb, handler);
        dbUtils.flush(pstat);
        
        IOTools.close(pstat);
        IOTools.close(dbUtils);
        */

        // It must retransmit the data from a temporary table to conctant tables and clear temptable
        //TableUtils.merge(dbUtils);
        //TableUtils.clearTempTable(dbUtils);
        String share = "smb://natalie.campus/incoming/! For/917/";//"smb://natalie.campus/incoming/! For/ForMiptShows/MiptShowsTest/";
        Refresher.updateDB(share);





    }
}
