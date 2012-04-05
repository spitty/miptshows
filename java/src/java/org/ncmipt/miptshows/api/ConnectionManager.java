package org.ncmipt.miptshows.api;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.LogManager;

/**
 *
 * @author Vlad, Roman
 */
public class ConnectionManager
{

    private static org.apache.log4j.Logger LOG = LogManager.getLogger(ConnectionManager.class);
    private static final String HOST = "http://api.myshows.ru/";
    private static final String PROFILE = "profile/";
    private static final String LOGIN = "login?";
    private static final String SHOWS = "shows/";
    private HttpClient httpClient;

    /**
     * getMd5Code convert input String into MD5
     *
     * @param value Input String for converting into MD5
     * @return      MD5 code
     * @throws      NoSuchAlgorithmException
     */
    public String getMd5Code(String value)
            throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(value.getBytes());

        byte byteData[] = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++)
        {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    /**
     * This function make authorization and return code 
     * which shows is authorization succeed or failed.
     * Also it creates the single HttpClient
     *
     * @param login    User login for authorization
     * @param password User password for authorization
     * @return         Server response status code
     */
    public int getAuthorization(String login, String password)
    {
        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
        HttpUriRequest httpRequest = null;
        HttpResponse response = null;
        try
        {
            password = getMd5Code(password);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("login", login));
            nameValuePairs.add(new BasicNameValuePair("password", password));

            httpRequest = new HttpGet(HOST + PROFILE + LOGIN + URLEncodedUtils.format(nameValuePairs, "UTF-8"));
            response = httpClient.execute(httpRequest);
        } catch (NoSuchAlgorithmException ex)
        {
            LOG.error("Failed make MD5", ex);
        } catch (IOException ex)
        {
            LOG.error("Failed make MD5", ex);
        }
        return response.getStatusLine().getStatusCode();
    }

    /**
     *
     * @return Server response as String
     */
    public String getListOfShows()
    {
        HttpPost httpPost = new HttpPost(HOST + PROFILE + SHOWS);
        StringBuilder sb = new StringBuilder("");
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            Scanner scanner = new Scanner(response.getEntity().getContent(), "UTF-8");
            while (scanner.hasNextLine())
            {
                sb.append(scanner.nextLine());
            }
        } catch (IOException ex)
        {
            LOG.error("Failed to create server response", ex);
        }
        return sb.toString();
    }

    //try to realize list of existing functions from api.myshows.ru
    /**
     * 
     * @param showId
     * @return 
     */
    public String getListOfViewedSeries(int showId)
    {
        HttpPost httpPost = new HttpPost(HOST + PROFILE + SHOWS + showId + '/');
        StringBuilder sb = new StringBuilder("");
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            Scanner scanner = new Scanner(response.getEntity().getContent(), "UTF-8");
            while (scanner.hasNextLine())
            {
                sb.append(scanner.nextLine());
            }
        } catch (IOException ex)
        {
            LOG.error("Failed to create server response", ex);
        }
        return sb.toString();
    }
    
//     public String mmmSeries(int episode)
//    {
//        HttpPost httpPost = new HttpPost(HOST + PROFILE + SHOWS +"3/"+ episode + '/');
//        StringBuilder sb = new StringBuilder("");
//        try
//        {
//            HttpResponse response = httpClient.execute(httpPost);
//            Scanner scanner = new Scanner(response.getEntity().getContent(), "UTF-8");
//            while (scanner.hasNextLine())
//            {
//                sb.append(scanner.nextLine());
//            }
//        } catch (IOException ex)
//        {
//            LOG.error("Failed to create server response", ex);
//        }
//        return sb.toString();
//    }

    /**
     * This function sets the value of rate for chosen show
     * 
     * @param showId Chosen show
     * @param rate 
     */
    public void manageShowRate(int showId, double rate)
    {
        String host = HOST + SHOWS + showId + "/" + rate + "/";
        HttpPost httpPost = new HttpPost(host);
    }

    /**
     * 
     * @return 
     */
    public String showTopAllShows()
    {
        HttpPost httpPost = new HttpPost(HOST + SHOWS + "top/all/");
        StringBuilder sb = new StringBuilder("");
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            Scanner scanner = new Scanner(response.getEntity().getContent(), "UTF-8");
            while (scanner.hasNextLine())
            {
                sb.append(scanner.nextLine());
            }
        } catch (IOException ex)
        {
            LOG.error("Failed to create server response", ex);
        }
        return sb.toString();
    }

    /**
     * 
     * @param login
     * @return 
     */
    public String getUserInfo(String login)
    {
        HttpPost httpPost = new HttpPost(HOST + PROFILE + login);
        StringBuilder sb = new StringBuilder("");
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            Scanner scanner = new Scanner(response.getEntity().getContent(), "UTF-8");
            while (scanner.hasNextLine())
            {
                sb.append(scanner.nextLine());
            }
        } catch (IOException ex)
        {
            LOG.error("Failed to create server response", ex);
        }
        return sb.toString();
    }
}
