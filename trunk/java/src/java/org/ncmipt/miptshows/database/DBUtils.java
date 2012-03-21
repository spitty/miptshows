package org.ncmipt.miptshows.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vlad, Roman
 */
public class DBUtils
{

    private Connection conn;

    public void connect() 
    {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oralce:thin:localhost:1522/orcl", "vlad", "vlad");
        
        } catch (SQLException ex)
        {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
}
