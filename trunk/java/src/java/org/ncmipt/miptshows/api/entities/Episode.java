/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ncmipt.miptshows.api.entities;

/**
 *
 * @author Vlad
 */
public class Episode
{
    int episodeId;
    String title;
    String showId;
    int seasonNumber;
    int episodeNumber;
    String airDate;
    String ref;

    public String getRef()
    {
        return ref;
    }

    public void setRef(String ref)
    {
        this.ref = ref;
    }

    public String getAirDate()
    {
        return airDate;
    }

    public void setAirDate(String airDate)
    {
        this.airDate = airDate;
    }

    public int getEpisodeId()
    {
        return episodeId;
    }

    public void setEpisodeId(int episodeId)
    {
        this.episodeId = episodeId;
    }

    public int getEpisodeNumber()
    {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber)
    {
        this.episodeNumber = episodeNumber;
    }

    public int getSeasonNumber()
    {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNmber)
    {
        this.seasonNumber = seasonNmber;
    }

    public String getShowId()
    {
        return showId;
    }

    public void setShowId(String showId)
    {
        this.showId = showId;
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
