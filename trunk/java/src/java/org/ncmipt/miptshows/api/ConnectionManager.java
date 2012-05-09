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
     * @throws NullPointerException
     */
    public int getAuthorization(String login, String password) throws NullPointerException
    {
        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
        HttpUriRequest httpRequest = null;
        HttpResponse response = null;
        int status = 0;
        try
        {
            password = getMd5Code(password);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("login", login));
            nameValuePairs.add(new BasicNameValuePair("password", password));

            httpRequest = new HttpGet("http://api.myshows.ru/profile/login?" + URLEncodedUtils.format(nameValuePairs, "UTF-8"));

            response = httpClient.execute(httpRequest);
            status = response.getStatusLine().getStatusCode();
        } catch (NoSuchAlgorithmException ex)
        {
            LOG.error("Failed make MD5", ex);
        } catch (IOException ex)
        {
            LOG.error("Failed make MD5", ex);
        }

        return status;
    }

    /**
     *
     * @return Server response as String
     */
    public String getListOfShows()
    {
        HttpPost httpPost = new HttpPost("http://api.myshows.ru/profile/shows/");
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
     * @param showId
     * @return
     */
    public String getListOfViewedSeries(int showId)
    {
        HttpPost httpPost = new HttpPost("http://api.myshows.ru/profile/shows/" + showId + '/');
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
     * This function sets the value of rate for chosen show identified by show id
     *
     * @param showId id of chosen show
     * @param rate
     */
    public void manageShowRate(String showId, int rate)
    {
        StringBuilder sb = new StringBuilder("http://api.myshows.ru/profile/shows/");
        sb.append(showId).append("/rate/").append(rate);
        HttpPost httpPost = new HttpPost(sb.toString());
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
        } catch (IOException ex)
        {
            LOG.error("Can't manage show rate", ex);
        }

    }

    /**
     * This function sets the value of rate for chosen show identified by episode id
     * @param episodeId id of chosen episode
     * @param rate
     */
    public void manageEpisodeRate(String episodeId, int rate)
    {
        StringBuilder sb = new StringBuilder("http://api.myshows.ru/profile/episodes/rate/");
        sb.append(rate).append('/').append(episodeId);
        HttpPost httpPost = new HttpPost(sb.toString());
        try
        {
            httpClient.execute(httpPost);
        } catch (IOException ex)
        {
            LOG.error("Can't manage episode rate", ex);
        }
    }

    /**
     *
     * @param episodeId
     */
    public void checkEpisode(String episodeId)
    {
        //http://api.myshows.ru/profile/episodes/check/291461
        String host = "http://api.myshows.ru/profile/episodes/check/" + episodeId;
        HttpPost httpPost = new HttpPost(host);
        try
        {
            httpClient.execute(httpPost);
        } catch (IOException ex)
        {
            LOG.error("Can't check episode", ex);
        }
    }

    /**
     *
     * @return
     */
    public String showTopAllShows()
    {
        HttpPost httpPost = new HttpPost("http://api.myshows.ru/shows/top/all/");
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
        HttpPost httpPost = new HttpPost("http://api.myshows.ru/profile/" + login);
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
     * @return
     */
    public String getUnwatchedEpisodes()
    {
        HttpPost httpPost = new HttpPost("http://api.myshows.ru/profile/episodes/unwatched/");
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
