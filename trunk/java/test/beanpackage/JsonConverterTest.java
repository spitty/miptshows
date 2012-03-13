package beanpackage;

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

        show.showId = "2";
        show.title = "The Big Bang Theory";
        show.ruTitle = "Теория большого взрыва";//"\\u0422\\u0435\\u043e\\u0440\\u0438\\u044f \\u0431\\u043e\\u043b\\u044c\\u0448\\u043e\\u0433\\u043e \\u0432\\u0437\\u0440\\u044b\\u0432\\u0430";
        show.runtime = 22;
        show.showStatus = "Returning Series";
        show.watchStatus = "later";
        show.watchedEpisodes = 0;
        show.totalEpisodes = 105;
        show.rating = 0;
        show.image = "http://images.tvrage.com/shows/9/8511.jpg";

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
            assertEquals(expResult.get(i).showId, result.get(i).showId);
            assertEquals(expResult.get(i).ruTitle, result.get(i).ruTitle);
            assertEquals(expResult.get(i).runtime, result.get(i).runtime, 0);
            assertEquals(expResult.get(i).showStatus, result.get(i).showStatus);
            assertEquals(expResult.get(i).watchStatus, result.get(i).watchStatus);
            assertEquals(expResult.get(i).watchedEpisodes, result.get(i).watchedEpisodes);
            assertEquals(expResult.get(i).totalEpisodes, result.get(i).totalEpisodes);
            assertEquals(expResult.get(i).rating, result.get(i).rating, 0);
            assertEquals(expResult.get(i).image, result.get(i).image);
        }
    }
}
