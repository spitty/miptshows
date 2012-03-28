package org.ncmipt.miptshows.api;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author Vlad, Roman
 */

public class ConnectionManager {

    private static final String HOST = "http://api.myshows.ru/profile/";
    private static final String LOGIN = "login?";
    private static final String SHOWS = "shows/";

    private HttpClient httpClient;

    /**
     *
     * @param value
     * @return MD5-code
     * @throws NoSuchAlgorithmException
     */
    public String getMd5code(String value)
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
     *
     * @param login
     * @param password
     * @return
     */
    public int getAuthorization(String login, String password)
    {
        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
        HttpUriRequest httpRequest = null;
        HttpResponse response = null;
        try
        {
            password = getMd5code(password);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("login", login));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            
            httpRequest = new HttpGet(HOST + LOGIN + URLEncodedUtils.format(nameValuePairs, "UTF-8"));
            response = httpClient.execute(httpRequest);
        }
        catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("request line:   " + httpRequest.getRequestLine());
        return response.getStatusLine().getStatusCode();
    }


    /**
     *
     * @return
     */
    public String getListOfShows ()
    {
        HttpPost httpPost = new HttpPost(HOST + SHOWS);
        StringBuilder sb = new StringBuilder("");
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            Scanner scanner = new Scanner(response.getEntity().getContent(), "UTF-8");
            //sb = new StringBuilder();
            while (scanner.hasNextLine())
            {
                sb.append(scanner.nextLine());
            }
        } catch (IOException ex)
        {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sb.toString();
    }
}
