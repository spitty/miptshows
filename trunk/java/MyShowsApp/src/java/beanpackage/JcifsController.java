package beanpackage;

import java.util.LinkedList;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
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
    public void login(String address, String username, String password) throws Exception
    {
        setDomain(UniAddress.getByName(address));
        setAuthentication(new NtlmPasswordAuthentication(address, username, password));
        SmbSession.logon(getDomain(), authentication);

    }

    /**
     *
     * @param path
     * @return
     * @throws Exception
     */
    public LinkedList<String> getList(String path) throws Exception
    {
        LinkedList<String> fList = new LinkedList<String>();
        SmbFile f = new SmbFile(path, authentication);
        SmbFile[] fArr = f.listFiles();

        for (int a = 0; a < fArr.length; a++)
        {
            fList.add(fArr[a].getName());
            System.out.println(fArr[a].getName());
        }

        return fList;
    }

    /**
     *
     * @param path
     * @return
     * @throws Exception
     */
    public boolean checkDirectory(String path) throws Exception
    {
        if (!isExist(path))
        {
            System.out.println(path + " not exist");
            return false;
        }

        if (!isDir(path))
        {
            System.out.println(path + " not a directory");
            return false;
        }

        return true;
    }

    /**
     *
     * @param path
     * @return
     * @throws Exception
     */
    public boolean isExist(String path) throws Exception
    {
        SmbFile sFile = new SmbFile(path, authentication);
        return sFile.exists();
    }

    /**
     *
     * @param path
     * @return
     * @throws Exception
     */
    public boolean isDir(String path) throws Exception
    {
        SmbFile sFile = new SmbFile(path, authentication);
        return sFile.isDirectory();
    }

    /**
     *
     * @param path
     * @throws Exception
     */
    public void createDir(String path) throws Exception
    {
        SmbFile sFile = new SmbFile(path, authentication);
        sFile.mkdir();
    }

    /**
     *
     * @param path
     * @throws Exception
     */
    public void delete(String path) throws Exception
    {
        SmbFile sFile = new SmbFile(path, authentication);
        sFile.delete();
    }

    /**
     *
     * @param path
     * @return
     * @throws Exception
     */
    public long size(String path) throws Exception
    {
        SmbFile sFile = new SmbFile(path, authentication);
        return sFile.length();
    }

    /**
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String getFileName(String path) throws Exception
    {
        SmbFile sFile = new SmbFile(path, authentication);
        return sFile.getName();
    }

}
