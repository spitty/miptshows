/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ncmipt.miptshows;

import java.util.ArrayList;
import java.util.List;
import org.ncmipt.miptshows.api.entities.Episode;
import org.ncmipt.miptshows.api.entities.Show;

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
     * 
     * @param shows
     * @param episodes
     * @return 
     */
    public List<Show> makeShowsWithEpisodes(List<Show> shows, List<Episode> episodes)
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
     * 
     * @param listOfShows
     * @return 
     */
    public List<List<Show>> makeListOfShowTypes(List<Show> listOfShows)
    {
        //index 0 - WATCHING
        //index 1 - LATER
        //index 2 - CANCELLED
        //index 3 - REMOVE
        List<List<Show>> typeList = new ArrayList<List<Show>>();//Hi J7!
        for (Show show : listOfShows)
        {
            String status = show.getWatchStatus();
            // Switch with Strings! Hi J7!

//            switch (status)
//            {
//                case WATHCING:
//                    typeList.get(0).add(show);
//                    break;
//                case LATER:
//                    typeList.get(1).add(show);
//                    break;
//                case CANCELLED:
//                    typeList.get(2).add(show);
//                    break;
//                case REMOVE:
//                    typeList.get(3).add(show);
//                    break;
//            }
            if (status.equals(WATHCING))
            {
                typeList.get(0).add(show);
            }
            if (status.equals(LATER))
            {
                typeList.get(1).add(show);
            }
            if (status.equals(CANCELLED))
            {
                typeList.get(2).add(show);
            }
            if (status.equals(REMOVE))
            {
                typeList.get(3).add(show);
            }
        }
        return typeList;

    }
}
