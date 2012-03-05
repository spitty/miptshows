package beanpackage;

/**
 *
 * @author Vlad, Roman
 */
public class Show {

    int watchedEpisodes;
    int totalEpisodes;
    float runtime;
    float rating;
    String showId;
    String title;
    String ruTitle;
    String image;
    String showStatus;
    String watchStatus;

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public float getRating()
    {
        return rating;
    }

    public void setRating(float rating)
    {
        this.rating = rating;
    }

    public String getRuTitle()
    {
        return ruTitle;
    }

    public void setRuTitle(String ruTitle)
    {
        this.ruTitle = ruTitle;
    }

    public float getRuntime()
    {
        return runtime;
    }

    public void setRuntime(float runtime)
    {
        this.runtime = runtime;
    }

    public String getShowId()
    {
        return showId;
    }

    public void setShowId(String showId)
    {
        this.showId = showId;
    }

    public String getShowStatus()
    {
        return showStatus;
    }

    public void setShowStatus(String showStatus)
    {
        this.showStatus = showStatus;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getTotalEpisodes()
    {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes)
    {
        this.totalEpisodes = totalEpisodes;
    }

    public String getWatchStatus()
    {
        return watchStatus;
    }

    public void setWatchStatus(String watchStatus)
    {
        this.watchStatus = watchStatus;
    }

    public int getWatchedEpisodes()
    {
        return watchedEpisodes;
    }

    public void setWatchedEpisodes(int watchedEpisodes)
    {
        this.watchedEpisodes = watchedEpisodes;
    }
    
}
