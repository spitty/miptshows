package org.ncmipt.miptshows.util;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ncmipt.miptshows.smb.FileObject;

/**
 *
 * @author Vlad, Roman
 */
public class DBUtils implements Closeable
{

    private Connection conn;
    private PreparedStatement pstatInsert;

    /**
     *
     */
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

    /**
     * 
     */
    public void createStatement()
    {
        try
        {
            pstatInsert = conn.prepareStatement("INSERT INTO temp_data "
                    + "(file_name, folder_name, file_size, server) VALUES (?, ?, ?, ?)");
        } catch (SQLException ex)
        {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param fileName
     * @param folderName
     * @param fileSize
     * @param server 
     */
    public void executeInsert(String fileName, String folderName, int fileSize, String server)
    {
        try
        {
            pstatInsert.setString(1, fileName);
            pstatInsert.setString(1, folderName);
            pstatInsert.setInt(1, fileSize);
            pstatInsert.setString(1, server);
            
            pstatInsert.addBatch();
            
        } catch (SQLException ex)
        {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     */
    public void executeInsert() 
    {
        try
        {
            pstatInsert.executeBatch();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param query 
     */
    public void executeUpdate(String query)
    {
        try
        {
            conn.createStatement().executeUpdate(query);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    /**
     * 
     * @param query 
     */
    public List<FileObject> executeQuery(String query)
    {
        ArrayList list = new ArrayList();
        FileObject file;
        
        try
        {
            ResultSet result = conn.createStatement().executeQuery(query);
            while (result.next()) 
            {
                file = new FileObject(result.getString(1), result.getString(2), 
                        result.getString(3), result.getInt(4));
                list.add(file);
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    
    @Override
    public void close()
    {
        IOTools.close(conn);
    }
}
