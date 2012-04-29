/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ncmipt.miptshows;

import java.util.ArrayList;
import java.util.List;
import org.ncmipt.miptshows.api.entities.Episode;
import org.ncmipt.miptshows.api.entities.Show;
import org.ncmipt.miptshows.util.TableUtils;

/**
 * The mission of this class is to do changes with Shows and List<Shows>
 * formed after JsonConverter to make typing of Show for following viewing;
 *
 * @author Vlad
 */
public class ListOfShowsChanger
{

    private static final String WATHCING = "watching";
    private static final String LATER = "later";
    private static final String CANCELLED = "cancelled";
    private static final String REMOVE = "remove";

    /**
     * This function adds in each show its unwatched episodes
     * @param shows
     * @param episodes
     * @return
     */
    public static List<Show> makeShowsWithEpisodes(List<Show> shows, List<Episode> episodes)
    {
        for (Show show : shows)
        {
            String showId = show.getShowId();
            for (Episode ep : episodes)
            {
                if (ep.getShowId().equals(showId))
                {
                    show.getListOfUnwathedEpisodes().add(ep);
                }
            }
        }
        return shows;
    }

    /**
     * makeListOfShowTypes separates shows by status.
     *
     * @param listOfShows
     * @return
     */
    public static List<List<Show>> makeListOfShowTypes(List<Show> listOfShows)
    {

        List<Show> listOfWatchingShows = new ArrayList<Show>();
        List<Show> listOfLaterShows = new ArrayList<Show>();
        List<Show> listOfCancelledShows = new ArrayList<Show>();
        List<Show> listOfRemoveShows = new ArrayList<Show>();
        for (Show show : listOfShows)
        {
            String status = show.getWatchStatus();

            if (status.equals(WATHCING))
            {
                listOfWatchingShows.add(show);
            }
            if (status.equals(LATER))
            {
                listOfLaterShows.add(show);
            }
            if (status.equals(CANCELLED))
            {
                listOfCancelledShows.add(show);
            }
            if (status.equals(REMOVE))
            {
                listOfRemoveShows.add(show);
            }

        }
        List<List<Show>> typeList = new ArrayList<List<Show>>();
        typeList.add(listOfWatchingShows);
        typeList.add(listOfLaterShows);
        typeList.add(listOfRemoveShows);
        typeList.add(listOfCancelledShows);

        return typeList;
    }

    /**
     *
     * @param shows
     * @return
     */
    public static List<Show> addRefToEpisodes(List<Show> shows)
    {
        for (Show show : shows)
        {
            List<Episode> episodes = show.getListOfUnwathedEpisodes();
            for (Episode ep : episodes)
            {
                List<String> pathes = TableUtils.getPathes(show.getRuTitle(),
                        show.getTitle(), ep.getSeasonNumber(), ep.getEpisodeNumber());
                for (String path : pathes)
                {
                    ep.setRef(path);
                }
            }
        }
        return shows;
    }
}
