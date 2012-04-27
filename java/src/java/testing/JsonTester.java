package testing;

import java.util.ArrayList;
import java.util.List;
import org.ncmipt.miptshows.api.ConnectionManager;
import org.ncmipt.miptshows.api.entities.Episode;

/**
 *
 * @author Vlad, Roman
 */
import org.ncmipt.miptshows.api.JsonConverter;

public class JsonTester
{

    public static void main(String[] args)
    {
        String text = "{"
                + "\"1429415\":{"
                + "\"episodeId\":1429415,"
                + "\"title\":\"The Hawking Excitation\","
                + "\"showId\":2,"
                + "\"seasonNumber\":5,"
                + "\"episodeNumber\":21,"
                + "\"airDate\":\"05.04.2012\"},"
                + "\"1429590\":{"
                + "\"episodeId\":1429590,"
                + "\"title\":\"Butterballs\","
                + "\"showId\":34,"
                + "\"seasonNumber\":16,"
                + "\"episodeNumber\":5,"
                + "\"airDate\":\"11.04.2012\"}"
                + "}";
        /*
        Gson gson = new Gson();
        Type typeOfT = new TypeToken<List<Episode>>(){}.getType();
        List<Episode> episodes = gson.fromJson(text, typeOfT);
         */
        
        ConnectionManager mgr = new ConnectionManager();
        String ep = mgr.getUnwatchedEpisodes();
        System.out.println(ep);
        /*
        List<Episode> episodes = new ArrayList<Episode>();
        episodes = JsonConverter.mapToEpisodes(text);
        for (Episode ep : episodes)
        {
            System.out.println(ep.getTitle());
        }
*/
    }
}
