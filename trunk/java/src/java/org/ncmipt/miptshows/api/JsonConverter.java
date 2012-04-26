package org.ncmipt.miptshows.api;

import org.ncmipt.miptshows.api.entities.Show;
import org.ncmipt.miptshows.api.entities.TopShow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ncmipt.miptshows.api.entities.Episode;

/**
 *
 * @author Vlad, Roman
 */
public class JsonConverter
{

    /**
     *
     * @param text
     * @return
     */
    public static List<Show> mapToShows(String text)
    {
        Gson gson = new Gson();
        Type typeOfT = new TypeToken<HashMap<String, Show>>()
        {
        }.getType();
        Map<String, Show> map = gson.fromJson(text, typeOfT);

        List<Show> list = new ArrayList<Show>();

        for (String st : map.keySet())
        {
            list.add(map.get(st));
        }
        return list;
    }

    /**
     *
     * @param text
     * @return
     */
    public static List<TopShow> mapToTopShows(String text)
    {
        Gson gson = new Gson();
        Type typeOfT = new TypeToken<List<TopShow>>(){}.getType();
        List<TopShow> topList = gson.fromJson(text, typeOfT);
        return topList;
    }

    public static List<Episode> mapToEpisodes(String text)
    {
         
        Gson gson = new Gson();
        Type typeOfT = new TypeToken<List<Episode>>(){}.getType();
        List<Episode> episodes = gson.fromJson(text,typeOfT);
        return episodes;
        
    }
    //TODO: create json-handler of viewed series
    //TODO: create json-handler of user info
}
