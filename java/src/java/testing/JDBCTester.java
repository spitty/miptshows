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
            ResultSet result = stat.executeQuery("select * from dual");
            while (result.next())
            {
                System.out.println(result.getString(1));
            }

            result.close();
            stat.close();
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
