package org.ncmipt.miptshows.smb;

/**
 *
 * @author spitty
 */
public class FileObject {
    private String name;
    private String folder;
    private int size;
    private String server;

    public FileObject(String name, String folder, String server, int size)
    {
        this.name = name;
        this.folder = folder;
        this.size = size;
        this.server = server;
    }

    
    public String getFolder()
    {
        return folder;
    }

    public void setFolder(String folder)
    {
        this.folder = folder;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getServer()
    {
        return server;
    }

    public void setServer(String server)
    {
        this.server = server;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }
    
}
