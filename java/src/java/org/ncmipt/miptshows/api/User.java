package org.ncmipt.miptshows.api;
import java.util.List;

/**
 *
 * @author Vlad
 */
public class User
{
    String login;
    String avatar;
    int wastedTime;
    char gender;
    List<User> friends;
    List<User> followers;

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public List<User> getFollowers()
    {
        return followers;
    }

    public void setFollowers(List<User> followers)
    {
        this.followers = followers;
    }

    public List<User> getFriends()
    {
        return friends;
    }

    public void setFriends(List<User> friends)
    {
        this.friends = friends;
    }

    public char getGender()
    {
        return gender;
    }

    public void setGender(char gender)
    {
        this.gender = gender;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public int getWastedTime()
    {
        return wastedTime;
    }

    public void setWastedTime(int wastedTime)
    {
        this.wastedTime = wastedTime;
    }
}
