package org.ncmipt.miptshows.util;

import java.io.Closeable;
import java.lang.reflect.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Util class simplifying IO operations<br/>
 * @author Spitty
 */
public class IOTools
{

    private static Log LOG = LogFactory.getLog(IOTools.class);

    /**
     * Private constructor. It prevents one from creating instance of {@link IOTools}
     */
    private IOTools()
    {
    }

    /**
     * This method tries to close
     * <code>resource</code> when it is a {@link Closeable} object
     *
     * @param resource {@link Closeable} object to be closed
     */
    public static void close(final Closeable resource)
    {
        try
        {
            if (resource != null)
            {
                resource.close();
            }
        } catch (Exception ex)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Can't close the resource", ex);
            }
        }
    }

    /**
     * This method tries to close
     * <code>resource</code> by invoking method <code>close</code>
     *
     * @param resource {@link Closeable} object to be closed
     */
    public static void close(final Object resource)
    {
        try
        {
            if (resource == null)
            {
                return;
            }
            final Method closeMethod = resource.getClass().getMethod("close", new Class[]
                    {
                    });
            closeMethod.invoke(resource, new Object[]
                    {
                    });
        } catch (Exception ex)
        {
            if (LOG.isErrorEnabled())
            {
                LOG.error("Can't close the resource", ex);
            }
        }
    }
}
