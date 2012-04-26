package org.ncmipt.miptshows.api.entities;

import java.util.List;

/**
 *
 * @author Vlad, Roman
 */
public class Show {

    int watchedEpisodes;
    int totalEpisodes;
    float runtime;
    double rating;
    String showId;
    String title;
    String ruTitle;
    String image;
    String showStatus;
    String watchStatus;
    List<Episode> list;

    public List<Episode> getList()
    {
        return list;
    }

    public void setList(List<Episode> list)
    {
        this.list = list;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public double  getRating()
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