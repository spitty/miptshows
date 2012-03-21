package org.ncmipt.miptshows.smb;

/**
 * Object implementing this interface can be passed into 
 * {@link JcifsController#scanFolder(java.lang.String, org.ncmipt.miptshows.smb.FileFindHandler)}
 * and process obtaining {@link FileObject}
 * @author Max
 */
public interface FileFindHandler {

    /**
     * This method is called by {@link JcifsController#scanFolder(java.lang.String, org.ncmipt.miptshows.smb.FileFindHandler)}
     * when it finds a file
     * @param file {@link FileObject} containing information about found object
     */
    public void onFileFound(FileObject file);
}
