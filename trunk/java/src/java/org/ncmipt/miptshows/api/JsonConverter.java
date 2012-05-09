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
     * This function parse JSON as input string <b>text</b> and make list of Shows from it
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
     * This function parse JSON as input string <b>text</b> and make list of Top Shows from it
     * @param text
     * @return
     */
    public static List<TopShow> mapToTopShows(String text)
    {
        Gson gson = new Gson();
        Type typeOfT = new TypeToken<List<TopShow>>()
        {
        }.getType();
        List<TopShow> topList = gson.fromJson(text, typeOfT);
        return topList;
    }

    /**
     * This function parse JSON as input string <b>text</b> and make list of Episodes from it
     * @param text
     * @return
     */
    public static List<Episode> mapToEpisodes(String text)
    {
        Gson gson = new Gson();
        Type typeOfT = new TypeToken<HashMap<String, Episode>>()
        {
        }.getType();
        Map<String, Episode> map = gson.fromJson(text, typeOfT);
        
        List<Episode> episodes = new ArrayList<Episode>();
        for (String st : map.keySet())
        {
            episodes.add(map.get(st));
        }
        return episodes;
    }
}
