/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vlad
 */
public class JDBCTester
{

    public static void main(String[] args)
    {
        try
        {
            Connection conn;
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522/orcl", "vlad", "vlad");

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("select * from users");
            while (result.next())
            {
                System.out.println(result.getString(1));
            }
            result.close();
            stat.close();

/*
            PreparedStatement pstat = conn.prepareStatement("INSERT INTO temp_data "
                    + "(file_name, folder_name, file_size, server) VALUES (?, ?, ?, ?)");

            pstat.setString(1, "file.vasya");
            pstat.setString(2, "fold.er_vasya");
            pstat.setInt(3, 1024);
            pstat.setString(4, "campus.vasya");
            pstat.addBatch();

            
            pstat.setString(1, "vl.ad");
            pstat.setString(2, "ro.ma");
            pstat.setInt(3, 124);
            pstat.setString(4, "vo.da");
            pstat.addBatch();
            
            
            pstat.executeBatch();
            conn.commit();
*/            

            
            conn.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(JDBCTester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(JDBCTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
