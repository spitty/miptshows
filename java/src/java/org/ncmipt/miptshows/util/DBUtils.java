package org.ncmipt.miptshows.util;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
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
     * Constructor of DBUtils class. It opens connection to DB and keeps result
     */
    public DBUtils()
    {

        try
        {
            String JNDIDataSourceName = PropertiesManager.getJNDIDataSourceName();
            InitialContext context = new InitialContext();
            DataSource source = (DataSource) context.lookup(JNDIDataSourceName);//"jdbc/miptshows"
            conn = source.getConnection();

        } catch (NamingException ex)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Constructor of DBUtils!", ex);
            }
        } catch (SQLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }
        }

    }

    /**
     * Create prepared statement for insertion
     * @return a ready prepared statement
     */
    public PreparedStatement createStatement()
    {
        PreparedStatement pstat = null;
        try
        {
            pstat = conn.prepareStatement("INSERT INTO temp_data "
                    + "(file_name, folder_name, file_size, server_name) VALUES (lower(?), ?, ?, ?)");
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
     * Execute a pool of insertions in prepared statement
     * @param pstat - a prepared statement with a pool of insertions
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
     * Execute the query
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
     * Execute search in data base
     * @param query
     * @return ResultSet
     */
    public ResultSet executeQuery(String query)
    {
        ResultSet result = null;
        try
        {
            Statement stat = conn.createStatement();
            result = stat.executeQuery(query);
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
     * Execute the query
     * @param query
     */
    public void execute(final String query)
    {
        if (query == null)
        {
            throw new IllegalArgumentException("query cannot be null");
        }

        try
        {
            Statement stat = conn.createStatement();
            stat.execute(query);

        } catch (SQLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB", e);
            }
        }
    }

    @Override
    public void close()
    {
        IOTools.close(conn);
    }
}
