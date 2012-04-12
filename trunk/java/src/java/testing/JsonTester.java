package testing;

import org.ncmipt.miptshows.api.entities.Show;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Vlad, Roman
 */
import java.util.Map;
import java.util.TreeSet;
import org.ncmipt.miptshows.api.entities.TopShow;
public class JsonTester
{

    public static void main(String[] args)
    {
        String text ="[{\"id\":1,"
                + "\"title\":\"House\","
                + "\"ruTitle\":\"\\u0414\u043e\u043a\\u0442\\u043e\\u0440 \\u0425\\u0430\\u0443\\u0441\","
                + "\"status\":\"Final Season\","
                + "\"year\":2004,"
                + "\"rating\":4.7692,"
                + "\"voted\":28861,"
                + "\"watching\":43586,"
                + "\"image\":\"http:\\/\\/images.tvrage.com\\/shows\\/4\\/3908.jpg\","
                + "\"place\":1}]";
        /*String text = "{\"2\":{\"showId\":2,"
                + "\"title\":\"The Big Bang Theory\","
                + "\"ruTitle\":\"\\u0422\\u0435\\u043e\\u0440\\u0438\\u044f \\u0431\\u043e\\u043b\\u044c\\u0448\\u043e\\u0433\\u043e \\u0432\\u0437\\u0440\\u044b\\u0432\\u0430\","
                + "\"runtime\":22,"
                + "\"showStatus\":\"Returning Series\","
                + "\"watchStatus\":\"later\","
                + "\"watchedEpisodes\":0,"
                + "\"totalEpisodes\":105,"
                + "\"rating\":0,"
                + "\"image\":\"http:\\/\\/images.tvrage.com\\/shows\\/9\\/8511.jpg\"},"

                + "\"1\":{\"showId\":1,"
                + "\"title\":\"House\","
                + "\"ruTitle\":\"\\u0414\\u043e\\u043a\\u0442\\u043e\\u0440 \\u0425\\u0430\\u0443\\u0441\","
                + "\"runtime\":43,"
                + "\"showStatus\":\"Final Season\","
                + "\"watchStatus\":\"watching\","
                + "\"watchedEpisodes\":0,"
                + "\"totalEpisodes\":167,"
                + "\"rating\":0,"
                + "\"image\":\"http:\\/\\/images.tvrage.com\\/shows\\/4\\/3908.jpg\"},"

                + "\"3\":{\"showId\":3,"
                + "\"title\":\"Lie to Me\","
                + "\"ruTitle\":\"\\u041e\\u0431\\u043c\\u0430\\u043d\\u0438 \\u043c\\u0435\\u043d\\u044f\","
                + "\"runtime\":43,"
                + "\"showStatus\":\"Canceled\\/Ended\","
                + "\"watchedEpisodes\":13,"
                + "\"totalEpisodes\":48,"                + "\"watchStatus\":\"watching\","

                + "\"rating\":0,"
                + "\"image\":\"http:\\/\\/images.tvrage.com\\/shows\\/20\\/19295.jpg\"}}";

         *
         */

       /* String text = "{\"1\":{\"showId\":2,"
                + "\"title\":\"The Big Bang Theory\"}}";
               /* + "\"ruTitle\":\"\\u0422\\u0435\\u043e\\u0440\\u0438\\u044f \\u0431\\u043e\\u043b\\u044c\\u0448\\u043e\\u0433\\u043e \\u0432\\u0437\\u0440\\u044b\\u0432\\u0430\","
                + "\"runtime\":22,"
                + "\"showStatus\":\"Returning Series\","
                + "\"watchStatus\":\"later\","
                + "\"watchedEpisodes\":0,"
                + "\"totalEpisodes\":105,"
                + "\"rating\":0,"
                + "\"image\":\"http:\\/\\/images.tvrage.com\\/shows\\/9\\/8511.jpg\"}}";

                 *
                 */

        Gson gson = new Gson();
        Type typeOfT = new TypeToken<List<TopShow>>(){}.getType();
        List<TopShow> topList = gson.fromJson(text, typeOfT);
        System.out.println(topList.get(0).getId());

        //System.out.println(list.get(0).getId());

 //       Map<String, Show> map = new HashMap<String, Show>();
   //     Type typeOfT = new TypeToken<HashMap<String, Show>>(){}.getType();
        //System.out.println(map.containsKey("1"));
     //   map = gson.fromJson(text, typeOfT);
        //System.out.println(map.get("2"));
       // List list = Arrays.asList(map);// Arrays.asList(map);
        //for(String st: map.keySet()){
          //  list.add(map.get(st));
//        }
        //System.out.println(list.get(1).title);
        //System.out.println(list.get(0));
        //Show show = gson.fromJson(map.get("1"), Show.class);
        //System.out.println(map.get("1").getClass());
        //System.out.println(map.get("1").get("title"));

         //Map sh = gson.fromJson(map.get("1"), map.getClass());
         //System.out.println(sh.toString());
         //System.out.println(map.get("1"));
//         System.out.println();
         //List al = Arrays.asList(map);
         //Show sh = (Show)al.get(10);
         //System.out.println(sh.title);
         /*for(Object st: al ){
             System.out.println(st.toString());
         }
          *
          */
         //System.out.println(map.values().toArray() );
        //System.out.println(show.title);
        /*for (Object o : show) {
            System.out.println(o);
        }
         *
         */

    }
}
class listOf {


}

class JsonUser
{

    String login;
    String avatar;
    String gender;
    int wastedTime;
    List<JsonUser> followers;
    Stats stats;
}

class Stats
{

    int watchedEpisodes;
    int remainingEpisodes;
    int totalEpisodes;
    double watchedHours;
    double remainingHours;
    double totalDays;
    double totalHours;
    double remainingDays;
    double watchedDays;
}

class Episode
{

    String title;
    int epid;

    public int getEpid()
    {
        return epid;
    }

    public void setEpid(int epid)
    {
        this.epid = epid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
