/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.util.ArrayList;
import java.util.List;
import org.ncmipt.miptshows.ListOfShowsChanger;
import org.ncmipt.miptshows.api.entities.Show;

/**
 *
 * @author Vlad
 */
public class ListOfShowsChangerTest
{

    private static final String WATHCING = "watching";
    private static final String LATER = "later";
    private static final String CANCELLED = "cancelled";
    private static final String REMOVE = "remove";

    public static void main(String[] args)
    {
        List<Show> shows = new ArrayList<Show>();
        
        Show sh1 = new Show();
        sh1.setWatchStatus(WATHCING);
        sh1.setTitle("sh1");

        Show sh2 = new Show();
        sh2.setWatchStatus(LATER);
        sh2.setTitle("sh2");

        Show sh3 = new Show();
        sh3.setWatchStatus(WATHCING);
        sh3.setTitle("sh3");

        Show sh4 = new Show();
        sh4.setWatchStatus(WATHCING);
        sh4.setTitle("sh4");

        Show sh5 = new Show();
        sh5.setWatchStatus(REMOVE);
        sh5.setTitle("sh5");
        
        shows.add(sh1);
        shows.add(sh2);
        shows.add(sh3);
        shows.add(sh4);
        shows.add(sh5);
        List<List<Show>> listOfShowTypes = ListOfShowsChanger.makeListOfShowTypes(shows);
        System.out.println(listOfShowTypes.get(0).get(0).getTitle());
        
    }
}
