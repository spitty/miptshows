package org.ncmipt.miptshows;

import org.ncmipt.miptshows.api.Show;
import org.ncmipt.miptshows.api.ConnectionManager;
import org.ncmipt.miptshows.api.JsonConverter;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.LogManager;

/**
 *
 * @author Vlad, Roman
 */
@ManagedBean
@SessionScoped
public class ActionBean
{

    //I suppose logger shouldn't be final. If we make Logger final we won't be able to add and change properties.
    private static org.apache.log4j.Logger LOG = LogManager.getLogger(ActionBean.class);
    private String response;
    private String login = "";
    private String password = "";
    private int status = 0;
    private int rating = 0;
    private String userInfo = "";
    private String topOfShows = "";
    private String viewedSeries = "";
    private List<Show> listOfShows;
    private ConnectionManager handler;

    // Block of getters & setters
    public String getTopOfShows()
    {
        return topOfShows;
    }

    public void setTopOfShows(String topOfShows)
    {
        this.topOfShows = topOfShows;
    }

    public String getUserInfo()
    {
        return userInfo;
    }

    public void setUserInfo(String userInfo)
    {
        this.userInfo = userInfo;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getResponse()
    {
        return response;
    }

    public void setResponse(String response)
    {
        this.response = response;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public List<Show> getListOfShows()
    {
        return listOfShows;
    }

    public void setListOfShows(List<Show> listOfShows)
    {
        this.listOfShows = listOfShows;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public String getViewedSeries()
    {
        return viewedSeries;
    }

    public void setViewedSeries(String viewedSeries)
    {
        this.viewedSeries = viewedSeries;
    }
    // End of the block

    public ActionBean()
    {
    }

    /**
     * 
     * @return greeting string
     */
    public String greeting()
    {
        return "Hi, " + this.login + "!";
    }

    /**
     *
     * @return
     */
    public String authorization()
    {
        listOfShows = null;
        handler = new ConnectionManager();
        status = handler.getAuthorization(login, password);
        String redirectTo;
        if (status == 200)
        {
            redirectTo = "actions.xhtml";
            LOG.debug("Authorization with login: " + login + "  and password: " + password + " succeeded");
        } else
        {
            FacesMessage fm = new FacesMessage("Authorization fails");
//            FacesContext.getCurrentInstance().addMessage("Authorization fails", fm);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authorization failed!", "Try again"));
            redirectTo = "";
            LOG.debug("Authorization failed");
        }

        return redirectTo;
    }

    /**
     *
     * @return
     */
    public List<Show> viewListOfShows()
    {
        response = handler.getListOfShows();
        listOfShows = JsonConverter.mapToShows(response);
        return listOfShows;
    }

    /**
     * 
     * 
     * @return 
     */
    public String userInfo()
    {
        userInfo = handler.getUserInfo(this.login);
        return userInfo;
    }

    /** 
     * This is debug function for getting authorization with 'SpringProjec' login and 'Spring@Project' password. 
     * Not waste our time for authorization in debugging process;
     */
    public void setDefaultAuth()
    {
        this.setPassword("spring@Project");
        this.setLogin("springProject");
    }

    /**
     * 
     */
    public void showTopAllShows()
    {
        topOfShows = handler.showTopAllShows();
    }

    /**
     * 
     */
    public void getListOfViewedSeries()
    {
        viewedSeries = handler.showTopAllShows();
    }
}