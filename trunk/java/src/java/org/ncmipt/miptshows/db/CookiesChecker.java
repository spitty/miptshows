package org.ncmipt.miptshows.db;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ncmipt.miptshows.util.IOTools;

/**
 *
 * @author Vlad
 */
public class CookiesChecker implements Closeable
{

    private static final Log LOG = LogFactory.getLog(CookiesChecker.class);
    private Connection conn;

    /**
     * Constructor of CookiesChecker with creating connection 
     * @return 
     */
    public CookiesChecker CookieChecker()
    {
        CookiesChecker checker = new CookiesChecker();
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oralce:thin:localhost:1522/orcl", "vlad", "vlad");
        } catch (ClassNotFoundException ex)
        {
            LOG.error("Can't make Class.forName", ex);
        } catch (SQLException ex)
        {
            LOG.error("Can't create connection", ex);
        }
        return checker;
    }

    /**
     * This function check existence of user with these login and password in our database.
     * @param login User login
     * @param password User password
     * @return true if user with these login and password is exist in our base<br/>
     * else -  return false
     */
    public boolean isUserExistInBase(String login, String password)
    {
        boolean isExist = false;
        try
        {
            Statement stat = conn.createStatement();
            String query = "SELECT login, password FROM users"
                    + "WHERE login = " + login
                    + "AND password = " + password;
            isExist = stat.execute(query);

        } catch (SQLException ex)
        {
            LOG.error("Can't create statement", ex);
        } finally
        {
            close();
        }
        return isExist;

    }

    /**
     * Function adds into our database new user with inputed login and password.
     * @param login User login
     * @param password User password
     */
    public void insertNewUserIntoBase(String login, String password)
    {
        try
        {
            Statement stat = conn.createStatement();
            String query = "INSERT INTO users(login, password) VALUES ('" + login + "','" + password + "')";
            stat.executeUpdate(query);
            stat.execute("commit");
        } catch (SQLException ex)
        {
            LOG.error("Can't create statement", ex);
        } finally
        {
            close();
        }
    }

    @Override
    public void close()
    {
        IOTools.close(conn);
    }
}
