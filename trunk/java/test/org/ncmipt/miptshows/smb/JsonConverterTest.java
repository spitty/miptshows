package org.ncmipt.miptshows.smb;

import org.ncmipt.miptshows.api.JsonConverter;
import org.ncmipt.miptshows.api.entities.Show;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vlad, Roman
 */
public class JsonConverterTest
{

    public JsonConverterTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of mapToShows method, of class JsonConverter.
     */
    @Test
    public void testMapToShows()
    {

        System.out.println("mapToShows");
        Show show = new Show();

        show.setShowId("2");
        show.setTitle("The Big Bang Theory");
        show.setRuTitle("Теория большого взрыва");//"\\u0422\\u0435\\u043e\\u0440\\u0438\\u044f \\u0431\\u043e\\u043b\\u044c\\u0448\\u043e\\u0433\\u043e \\u0432\\u0437\\u0440\\u044b\\u0432\\u0430";
        show.setRuntime(22);
        show.setShowStatus("Returning Series");
        show.setWatchStatus("later");
        show.setWatchedEpisodes(0);
        show.setTotalEpisodes(105);
        show.setRating("0");
        show.setImage("http://images.tvrage.com/shows/9/8511.jpg");

        String text = "{\"2\":{\"showId\":2,"
                + "\"title\":\"The Big Bang Theory\","
                + "\"ruTitle\":\"\\u0422\\u0435\\u043e\\u0440\\u0438\\u044f \\u0431\\u043e\\u043b\\u044c\\u0448\\u043e\\u0433\\u043e \\u0432\\u0437\\u0440\\u044b\\u0432\\u0430\","
                + "\"runtime\":22,"
                + "\"showStatus\":\"Returning Series\","
                + "\"watchStatus\":\"later\","
                + "\"watchedEpisodes\":0,"
                + "\"totalEpisodes\":105,"
                + "\"rating\":0,"
                + "\"image\":\"http:\\/\\/images.tvrage.com\\/shows\\/9\\/8511.jpg\"}}";

        List<Show> expResult = new ArrayList<Show>();
        expResult.add(show);

        List<Show> result = JsonConverter.mapToShows(text);

        for (int i = 0; i < result.size(); i++)
        {
            assertEquals(expResult.get(i).getShowId(), result.get(i).getShowId());
            assertEquals(expResult.get(i).getRuTitle(), result.get(i).getRuTitle());
            assertEquals(expResult.get(i).getRuntime(), result.get(i).getRuntime(), 0);
            assertEquals(expResult.get(i).getShowStatus(), result.get(i).getShowStatus());
            assertEquals(expResult.get(i).getWatchStatus(), result.get(i).getWatchStatus());
            assertEquals(expResult.get(i).getWatchedEpisodes(), result.get(i).getWatchedEpisodes());
            assertEquals(expResult.get(i).getTotalEpisodes(), result.get(i).getTotalEpisodes());
            assertEquals(expResult.get(i).getRating(), result.get(i).getRating(), "0");
            assertEquals(expResult.get(i).getImage(), result.get(i).getImage());
        }
    }
}
