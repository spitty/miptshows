/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import org.ncmipt.miptshows.JcifsController;
import com.sun.net.httpserver.HttpServer;
import java.util.List;
import jcifs.smb.SmbFile;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Vlad
 */
public class JcifsTester
{

    public static void main(String[] args) throws Exception
    {
        
        JcifsController jcc = new JcifsController();
        
        List<String> list = jcc.getListOfFiles("smb://video.campus/video/all/by tag/quality/1080p/500 Days of Summer [500 дней лета] (2009)[movie]/1080p.en,ru/");
        for (String str: list) 
        {
            System.out.println(str);
        }
        
        /*
        SmbFile sf = new SmbFile("smb://video.campus/video/all/by tag/quality/1080p/500 Days of Summer [500 дней лета] (2009)[movie]/1080p.en,ru/");
        SmbFile[] sfs = sf.listFiles();
        
        
        for(SmbFile s: sfs){
            System.out.println(s.getName());
        }
        */
        
        
        /*HttpClient hc = new DefaultHttpClient();
        HttpServer hs = new         
    /*SmbFile[] servers = workgroup.listFiles();
    for (int i = 0; i < servers.length; i++) {
      long startTime = System.currentTimeMillis();
      try {
        servers[i].listFiles();
      }
      catch (Exception ex) { }
      System.out.println(servers[i] + " listFiles() took " +
            (System.currentTimeMillis() - startTime) + " milliseconds");
    }
       /* SmbFile file = new SmbFile("smb:\\\\video.campus\\video\\all\\by tag\\quality\\1080p\\"); 
		SmbFile[] files = file.listFiles();
		System.out.println("acssed done");

		for (int i = 0; i < files.length; i++)
		{
			String name = files[i].getName();
			System.out.println(name);
		}

        /*
        URL url = new URL("\\\\video.campus\\video\\all\\by tag\\quality\\1080p\\500 Days of Summer [500 дней лета] (2009)[movie]\\1080p.en,ru\\about.txt");
        SmbFile sf = new SmbFile(url);
        SmbFileInputStream stream = new SmbFileInputStream(sf);
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        System.out.println(br.readLine());

        /*boolean b = jcc.isExist("smb:\\\\video.campus\\video\\all\\by tag\\quality\\1080p\\500 Days of Summer [500 дней лета] (2009)[movie]\\1080p.en,ru");
        System.out.println(b);
        System.out.println(jcc.isDirectory("smb:\\\\video.campus\\video\\all\\by tag\\quality\\1080p\\500 Days of Summer [500 дней лета] (2009)[movie]\\1080p.en,ru\\about.txt"));
        
        /*SmbFile sf = new SmbFile("smb:\\\\video.campus\\video\\all\\by tag\\quality\\1080p\\500 Days of Summer [500 дней лета] (2009)[movie]\\1080p.en,ru\\about.txt");
        System.out.println(sf.getInputStream());
        /*
        System.out.println(
        sf.getLastModified());
         * 
         */
    }
}
