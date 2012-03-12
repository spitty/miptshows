package beanpackage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;

/**
 *
 * @author Vlad, Roman
 */
public class JcifsController
{

    private NtlmPasswordAuthentication authentication;
    private UniAddress domain;

    // A block of getters & setters
    public NtlmPasswordAuthentication getAuthentication()
    {
        return authentication;
    }

    public void setAuthentication(NtlmPasswordAuthentication authentication)
    {
        this.authentication = authentication;
    }

    public UniAddress getDomain()
    {
        return domain;
    }

    public void setDomain(UniAddress domain)
    {
        this.domain = domain;
    }
    // End of the block

    /**
     *
     * @param address
     * @param username
     * @param password
     * @throws Exception
     */
    public void login(String address, String username, String password)
            throws Exception
    {
        setDomain(UniAddress.getByName(address));
        setAuthentication(new NtlmPasswordAuthentication(address, username, password));
        SmbSession.logon(domain, authentication);
    }

    /**
     *
     * @param path
     * @return
     * @throws Exception
     */
    public boolean isExist(String path)
            throws Exception
    {
        SmbFile sf = new SmbFile(path);
        return sf.exists();
    }

    /**
     *
     * @param path
     * @return
     * @throws Exception
     */
    public boolean isDirectory(String path)
            throws Exception
    {
        SmbFile sf = new SmbFile(path);
        return sf.isDirectory();
    }

    /**
     *
     * @param path
     * @throws Exception
     */
    public void createDirectory(String path)
            throws Exception
    {
        SmbFile sf = new SmbFile(path);
        sf.mkdir();
    }

    /**
     *
     * @param path
     * @throws Exception
     */
    public void delete(String path)
            throws Exception
    {
        SmbFile sf = new SmbFile(path);
        sf.delete();
    }

    /**
     *
     * @param path
     * @return
     * @throws Exception
     */
    public long size(String path)
            throws Exception
    {
        SmbFile sf = new SmbFile(path);
        return sf.length();
    }

    /**
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String getFileName(String path)
            throws Exception
    {
        SmbFile sf = new SmbFile(path, authentication);
        return sf.getName();
    }

    ///////////////////////////////////////////////////////////////////
    
    private HashMap<String, String> map = new HashMap<String, String>();
    
    private void fillMap(String cataloge)
    {
        try
        {
            SmbFile sfile = new SmbFile(cataloge);
            SmbFile[] sfs = sfile.listFiles();

            for (SmbFile sf : sfs)
            {
                if (sf.isDirectory())
                {
                    fillMap(sf.getPath());
                }
                else 
                {
                    map.put(sf.getName(), sf.getPath());
                }
            }
            
        } catch (SmbException ex)
        {
            Logger.getLogger(JcifsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex)
        {
            Logger.getLogger(JcifsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<String> getListOfFiles(String root) throws IOException{
        List<String> list = new ArrayList<String>();
        try
        {
            SmbFile sf = new SmbFile(root);
            if ( !sf.exists() )
            {
                throw new IOException();    // complete it!!!
            }
            
            if ( sf.isFile() )
            {
                map.put(sf.getName(), sf.getPath());
            }
            else if ( sf.isDirectory() )
            {
                fillMap(root);
            }
            
        } catch (SmbException ex)
        {
            Logger.getLogger(JcifsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex)
        {
            Logger.getLogger(JcifsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(String str: map.keySet()){
            list.add(map.get(str));
        }
        
        return list;
    }
    
}
