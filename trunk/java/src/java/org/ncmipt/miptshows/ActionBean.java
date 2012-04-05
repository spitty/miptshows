package org.ncmipt.miptshows;

import org.ncmipt.miptshows.api.Show;
import org.ncmipt.miptshows.api.ConnectionManager;
import org.ncmipt.miptshows.api.JsonConverter;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.log4j.LogManager;
import org.ncmipt.miptshows.api.TopShow;
import org.primefaces.event.RateEvent;

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
    private String userInfo = "";
    private String viewedSeries = "";
    private List<Show> listOfShows;
    private List<TopShow> listOfTopAllShows;
    private ConnectionManager handler;
    private boolean count;

    // Block of getters & setters
    public List<TopShow> getListOfTopAllShows()
    {
        return listOfTopAllShows;
    }

    public void setListOfTopAllShows(List<TopShow> listOfTopAllShows)
    {
        this.listOfTopAllShows = listOfTopAllShows;
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

    public String getViewedSeries()
    {
        return viewedSeries;
    }

    public void setViewedSeries(String viewedSeries)
    {
        this.viewedSeries = viewedSeries;
    }

    public void setCount(boolean count)
    {
        this.count = count;
    }
    // End of the block setters and getters

    public ActionBean()
    {
    }

    /**
     * This function make a greeting string using the user's login.
     * 
     * @return greeting String
     */
    public String greeting()
    {
        return "Hi, " + this.login + "!";
    }

    /**
     * This function trying to do authorization with imputed login and MD5-converted password.
     * 
     * @return redirectTo name of the further page
     */
    public String authorization()
    {
        listOfShows = null;
        handler = new ConnectionManager();
        status = handler.getAuthorization(login, password);
        String redirectTo;
        if (status == 200)
        {
            redirectTo = "actions";
            LOG.debug("Authorization with login: " + login + "  and password: " + password + " succeeded");
        } else
        {
            FacesMessage fm = new FacesMessage("Authorization fails");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authorization failed!", "Try again"));
            redirectTo = "";
            LOG.debug("Authorization failed");
        }
        return redirectTo;
    }

    /**
     * This function create list of  shows from JSON, 
     * obtained from server response and converted by JsonConverter into List
     * 
     * @return List<Show>
     */
    public void makeListOfShows()
    {
        response = handler.getListOfShows();
        listOfShows = JsonConverter.mapToShows(response);
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
     * This is debug function for getting authorization with 'SpringProject' login and 'Spring@Project' password. 
     * Not waste our time for authorization in debugging process;
     */
    public void setDefaultAuth()
    {
        this.setPassword("spring@Project");
        this.setLogin("springProject");
    }

    /**
     * This function create a List of TopShows where positions are ordered by rating
     * 
     */
    public void makeListOfTopAllShows()
    {
        String json = handler.showTopAllShows();
        listOfTopAllShows = JsonConverter.mapToTopShows(json);
    }

   /**
     * 
     * @param ShowId 
     */
    public void makeListOfViewedSeries(int ShowId)
    {
        viewedSeries = handler.getListOfViewedSeries(ShowId);
    }

    
    public void manageShowRate(RateEvent rateEvent)
    {
        String id = rateEvent.getComponent().getId();
        double rat = ((Double) rateEvent.getRating()).intValue();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rate Event", "You rated:" + rat);
        FacesContext.getCurrentInstance().addMessage(null, message);
        //handler.manageShowRate(showId, rat);
    }

    public void doCookies(HttpRequest request, HttpResponse response)
    {
        Cookie loginCookie = new BasicClientCookie2("login", login);
        Cookie passwCookie = new BasicClientCookie2("password", password);
        //How to add cookies to response?
    }
}