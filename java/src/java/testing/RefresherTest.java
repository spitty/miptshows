
package testing;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;
import jcifs.smb.SmbException;
import org.ncmipt.miptshows.db.Updater;
import org.ncmipt.miptshows.util.TableUtils;

/**
 *
 * @author Vlad
 */
public class RefresherTest
{

    public static void main(String[] args) throws SQLException, MalformedURLException, SmbException
    {

        String share = "smb://natalie.campus/incoming/! For/ForMiptShows/MiptShowsTest/";//"smb://natalie.campus/incoming/! For/ForMiptShows/MiptShowsTest/";
        Updater.updateDB(share);
        List<String> pathes = TableUtils.getPathes("Tractor Man", "ru", 1, 2);

        System.out.println(pathes.size());
        for(String path : pathes)
        {
            System.out.println(path);
        }

    }
}
