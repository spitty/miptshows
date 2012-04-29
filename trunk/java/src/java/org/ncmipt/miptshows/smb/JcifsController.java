package org.ncmipt.miptshows.smb;

import java.net.MalformedURLException;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Vlad, Roman
 */
public class JcifsController
{

    private static final Log LOG = LogFactory.getLog(JcifsController.class);

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
            if (!smbFile.exists())
            {
                if (LOG.isErrorEnabled())
                {
                    LOG.error("Can't find SMB resource by path " + folderPath);
                }
                return;
            }

            scanFolder(smbFile, handler);
        } catch (SmbException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong with DB ", e);
            }
        } catch (MalformedURLException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Smth wrong ", e);
            }
        }
    }

    /**
     * This method recursively discover SMB resource
     * <code>folderPath</code> and invoke {@link FileFindHandler#onFileFound(org.ncmipt.miptshows.smb.FileObject) handler.onFileFound}
     * method on each found file
     *
     * @param smbFile samba file needing to scan
     * @param handler {@link FileFindHandler} object to operate with results
     */
    public static void scanFolder(final SmbFile smbFile, final FileFindHandler handler)
    {
        if (smbFile == null)
        {
            throw new IllegalArgumentException("SmbFile can't be null");
        }

        try
        {
            if (!smbFile.exists())
            {
                if (LOG.isErrorEnabled())
                {
                    LOG.error("Can't find SMB resource by path " + smbFile.getCanonicalPath());
                }
                return;
            }
            if (smbFile.isFile())
            {
                handler.onFileFound(new FileObject(smbFile.getName(), smbFile.getParent(),
                        smbFile.getServer(), smbFile.getContentLength()));

            } else if (smbFile.isDirectory())
            {
                final SmbFile files[] = smbFile.listFiles();
                for (SmbFile file : files)
                {
                    scanFolder(file, handler);
                }
            }
        } catch (SmbException e)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Error during processing SMB resource " + smbFile.getCanonicalPath(), e);
            }
        }
    }
}
