
package org.ncmipt.miptshows.api.entities;

/**
 *
 * @author Vlad
 */
public class Stats
{
    int watchedHours;
    double remainingHours;
    int watchedEpisodes;
    int remainingEpisodes;
    int totalEpisodes;
    int totalDays;
    int totalHours;
    double remainingDays;
    int watchedDays;

    public double getRemainingDays()
    {
        return remainingDays;
    }

    public void setRemainingDays(double remainingDays)
    {
        this.remainingDays = remainingDays;
    }

    public int getRemainingEpisodes()
    {
        return remainingEpisodes;
    }

    public void setRemainingEpisodes(int remainingEpisodes)
    {
        this.remainingEpisodes = remainingEpisodes;
    }

    public double getRemainingHours()
    {
        return remainingHours;
    }

    public void setRemainingHours(double remainingHours)
    {
        this.remainingHours = remainingHours;
    }

    public int getTotalDays()
    {
        return totalDays;
    }

    public void setTotalDays(int totalDays)
    {
        this.totalDays = totalDays;
    }

    public int getTotalEpisodes()
    {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes)
    {
        this.totalEpisodes = totalEpisodes;
    }

    public int getTotalHours()
    {
        return totalHours;
    }

    public void setTotalHours(int totalHours)
    {
        this.totalHours = totalHours;
    }

    public int getWatchedDays()
    {
        return watchedDays;
    }

    public void setWatchedDays(int watchedDays)
    {
        this.watchedDays = watchedDays;
    }

    public int getWatchedEpisodes()
    {
        return watchedEpisodes;
    }

    public void setWatchedEpisodes(int watchedEpisodes)
    {
        this.watchedEpisodes = watchedEpisodes;
    }

    public int getWatchedHours()
    {
        return watchedHours;
    }

    public void setWatchedHours(int watchedHours)
    {
        this.watchedHours = watchedHours;
    }
}
