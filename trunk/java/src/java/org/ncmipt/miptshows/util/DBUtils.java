package org.ncmipt.miptshows.util;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ncmipt.miptshows.properties.PropertiesManager;

/**
 *
 * @author Vlad, Roman
 */
public class DBUtils implements Closeable
{

    private Connection conn;

    public Connection getConn()
    {
        return conn;
    }
    private static final Log LOG = LogFactory.getLog(DBUtils.class);

    /**
     * Constructor of DBUtils class. It executes connection to DB and keeps result
     * @return an instance of current class
     */
    public DBUtils()
    {
     
        try
        {
            String driver = PropertiesManager.getDatabaseDriver();
            String url = PropertiesManager.getDatabaseURL();
            String username = PropertiesManager.getDatabaseUserName();
            String password = PropertiesManager.getDatabaseUserPassword();
           
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);

        } catch (SQLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }
        } catch (ClassNotFoundException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }
        }
    }

    /**
     *
     * @return
     */
    public PreparedStatement createStatement()
    {
        PreparedStatement pstat = null;
        try
        {
            pstat = conn.prepareStatement("INSERT INTO temp_data "
                    + "(file_name, folder_name, file_size, server) VALUES (?, ?, ?, ?)");
        } catch (SQLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }
        }
        return pstat;
    }

    /**
     *
     * @param pstat
     * @return
     */
    public int[] flush(PreparedStatement pstat)
    {
        int[] results = null;
        try
        {
            results = pstat.executeBatch();
        } catch (SQLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }
        }
        return results;
    }

    /**
     *
     * @param query
     * @return
     */
    public int executeUpdate(String query)
    {
        if (query == null)
        {
            throw new IllegalArgumentException("query cannot be null");
        }

        int result = 0;

        try
        {
            Statement stat = conn.createStatement();
            result = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }
        }

        return result;
    }

    /**
     *
     * @param query
     * @return
     */
    public ResultSet executeQuery(String query)
    {
        ResultSet result = null;
        try
        {
            Statement stat = conn.createStatement();
            result = stat.executeQuery(query);
            stat.close();
        } catch (SQLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }
        }

        return result;
    }

    /**
     *
     * @param query
     * @return
     */
    public boolean execute(final String query)
    {
        if (query == null)
        {
            throw new IllegalArgumentException("query cannot be null");
        }

        boolean isSuccess = false;

        try
        {
            Statement stat = conn.createStatement();
            stat.execute(query);
            stat.close();
        } catch (SQLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }
        }

        return isSuccess;
    }

    @Override
    public void close()
    {
        IOTools.close(conn);
    }
}

