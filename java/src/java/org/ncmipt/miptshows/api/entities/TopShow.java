/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ncmipt.miptshows.api.entities;

/**
 *
 * @author Vlad
 */
public class TopShow
{

    int id;
    String title;
    String ruTitle;
    String status;
    int year;
    double rating;
    int voted;
    int watching;
    String image;
    int place;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public int getPlace()
    {
        return place;
    }

    public void setPlace(int place)
    {
        this.place = place;
    }

    public double getRating()
    {
        return rating;
    }

    public void setRating(double rating)
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getVoted()
    {
        return voted;
    }

    public void setVoted(int voted)
    {
        this.voted = voted;
    }

    public int getWatching()
    {
        return watching;
    }

    public void setWatching(int watching)
    {
        this.watching = watching;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }
}
