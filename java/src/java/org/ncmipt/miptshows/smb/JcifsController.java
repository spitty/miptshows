package org.ncmipt.miptshows.smb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.log4j.LogManager;

/**
 *
 * @author Vlad, Roman
 */
public class JcifsController
{

    private static final org.apache.log4j.Logger LOG = LogManager.getLogger(JcifsController.class);

    @Deprecated
    private Map<String, String> map = new HashMap<String, String>();

    /**
     * This method recursively discover SMB resource
     * <code>folderPath</code> and invoke {@link FileFindHandler#onFileFound(org.ncmipt.miptshows.smb.FileObject) handler.onFileFound}
     * method on each found file
     *
     * @param folderPath folder on a server to scan
     * @param handler {@link FileFindHandler} object to operate with results
     */
    public static void scanFolder(final String folderPath, final FileFindHandler handler)
    {
        try
        {
            final SmbFile smbFile = new SmbFile(folderPath);
            if(!smbFile.exists())
            {
                LOG.error("Can't find SMB resource by path " + folderPath);
                return;
            }

            scanFolder(smbFile, handler);
        }
        catch(SmbException ex)
        {
            Logger.getLogger(JcifsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(MalformedURLException ex)
        {
            Logger.getLogger(JcifsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void scanFolder(final SmbFile smbFile, final FileFindHandler handler)
    {
        if(smbFile == null)
        {
            throw new IllegalArgumentException("SmbFile can't be null");
        }

        try
        {
            if(!smbFile.exists())
            {
                LOG.error("Can't find SMB resource by path " + smbFile.getCanonicalPath());
                return;
            }

            if(smbFile.isFile())
            {

                handler.onFileFound(new FileObject(smbFile.getName(), smbFile.getParent(), smbFile.getServer(), smbFile.getContentLength()));
            }
            else if(smbFile.isDirectory())
            {
                final SmbFile files[] = smbFile.listFiles();
                for(SmbFile file : files)
                {
                    scanFolder(file, handler);
                }
            }
        }
        catch(SmbException ex)
        {
            LOG.error("Error during processing SMB resource " + smbFile.getCanonicalPath());
            Logger.getLogger(JcifsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is marked as {@link Deprecated} because of its logical
     * structure. It saves information about obtaining files in a map but in our
     * case it is more convenient to process files separately or by small
     * groups.
     *
     * @param cataloge
     * @deprecated
     */
    @Deprecated
    private void fillMap(String cataloge)
    {
        try
        {
            SmbFile sfile = new SmbFile(cataloge);
            SmbFile[] sfs = sfile.listFiles();

            for(SmbFile sf : sfs)
            {
                if(sf.isDirectory())
                {
                    fillMap(sf.getPath());
                }
                else
                {
                    map.put(sf.getName(), sf.getPath());
                }
            }

        }
        catch(SmbException ex)
        {
            Logger.getLogger(JcifsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(MalformedURLException ex)
        {
            Logger.getLogger(JcifsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Deprecated
    public List<String> getListOfFiles(String root) throws IOException
    {
        List<String> list = new ArrayList<String>();
        try
        {
            SmbFile sf = new SmbFile(root);
            if(!sf.exists())
            {
                throw new IOException();    // complete it!!!
            }

            if(sf.isFile())
            {
                map.put(sf.getName(), sf.getPath());
            }
            else if(sf.isDirectory())
            {
                fillMap(root);
            }

        }
        catch(SmbException ex)
        {
            Logger.getLogger(JcifsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(MalformedURLException ex)
        {
            Logger.getLogger(JcifsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        for(String str : map.keySet())
        {
            list.add(map.get(str));
        }

        return list;
    }

    /**
     * Just for example. Remove it later
     *
     * @param args
     * @throws MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException
    {
        SmbFile sf = new SmbFile("smb://natalie.campus/incoming/! For/917/");
//        System.out.println(sf.getPath());
//        System.out.println(sf.getCanonicalPath());
//        System.out.println(sf.getParent());

        JcifsController.scanFolder(sf, new FileFindHandler()
        {

            @Override
            public void onFileFound(FileObject file)
            {
                // String concatenation is a very very bad practice. But it is just an example =)
                System.out.println("Obtaining " + file.getName() + "(" + file.getFolder() + ")");
            }
        });
    }
}
