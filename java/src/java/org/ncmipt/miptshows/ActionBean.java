package org.ncmipt.miptshows;

import java.io.IOException;
import java.io.Serializable;
import org.ncmipt.miptshows.api.ConnectionManager;
import org.ncmipt.miptshows.api.JsonConverter;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.LogManager;
import org.ncmipt.miptshows.api.entities.Episode;
import org.ncmipt.miptshows.api.entities.Show;
import org.ncmipt.miptshows.api.entities.TopShow;
import org.ncmipt.miptshows.properties.PropertiesManager;
import org.primefaces.event.RateEvent;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author Vlad, Roman
 */
@ManagedBean
@SessionScoped
public class ActionBean implements Serializable
{
    
    private static final org.apache.log4j.Logger LOG = LogManager.getLogger(ActionBean.class);
    private boolean auth = false;
    private String applicationName = PropertiesManager.getApplicationName();
    private String resp;
    private String login = "";
    private String password = "";
    private int status = 0;
    private String userInfo = "";
    private String viewedSeries = "";
    private List<Show> listOfShows;
    private List<Show> listOfWatchingShows;
    private List<Show> listOfLaterShows;
    private List<Show> listOfCancelledShows;
    private List<TopShow> listOfTopAllShows;
    private List<Show> listOfRemoveShows;
    private ConnectionManager handler;
    private List<Episode> ep;
    
    public List<Episode> getEp()
    {
        return ep;
    }
    
    public void setEp(List<Episode> ep)
    {
        this.ep = ep;
    }

    /********************** Block of setters and getters ***********************/
    public String getApplicationName()
    {
        return applicationName;
    }
    
    public List<Show> getListOfCancelledShows()
    {
        return listOfCancelledShows;
    }
    
    public void setListOfCancelledShows(List<Show> listOfCancelledShows)
    {
        this.listOfCancelledShows = listOfCancelledShows;
    }
    
    public List<Show> getListOfLaterShows()
    {
        return listOfLaterShows;
    }
    
    public void setListOfLaterShows(List<Show> listOfLaterShows)
    {
        this.listOfLaterShows = listOfLaterShows;
    }
    
    public List<Show> getListOfRemoveShows()
    {
        return listOfRemoveShows;
    }
    
    public void setListOfRemoveShows(List<Show> listOfRemoveShows)
    {
        this.listOfRemoveShows = listOfRemoveShows;
    }
    
    public List<Show> getListOfWatchingShows()
    {
        return listOfWatchingShows;
    }
    
    public void setListOfWatchingShows(List<Show> listOfWatchingShows)
    {
        this.listOfWatchingShows = listOfWatchingShows;
    }
    
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
    
    public String getResp()
    {
        return resp;
    }
    
    public void setResp(String resp)
    {
        this.resp = resp;
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

    /************************** End of the block setters and getters *****************************/
    public ActionBean()
    {
    }

    /**
     * This function make a greeting string using the user's login.
     * @return greeting String
     */
    public String greeting()
    {
        StringBuilder sb = new StringBuilder("Hi, ");
        sb.append(this.login).append("!");
        return sb.toString();
    }

    /**
     * This function trying to do authorization with imputed login and MD5-converted password.
     * @return redirectTo name of the further page
     */
    public String authorization()
    {
        listOfShows = null;
        handler = new ConnectionManager();
        String redirectTo = "";
        try
        {
            status = handler.getAuthorization(login, password);
            
            if (status == 200)
            {
                this.auth = true;
                redirectTo = "actions";
                LOG.debug("Authorization with login: " + login + "  and password: " + password + " succeeded");
            } else
            {
                this.auth = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authorization failed!", "Try again"));
                redirectTo = "";
                LOG.debug("Authorization failed");
            }
        } catch (NullPointerException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authorization failed! Connection aborted.", "Try again"));
            LOG.debug("Authorization with login: " + login + "  and password: " + password + " failed. Connection aborted");
            return "";
        }
        return redirectTo;
    }

    /**
     * This function create list of  shows from JSON,
     * obtained from server response and converted by JsonConverter into List
     */
    public void makeListOfShows()
    {
        
        String response = handler.getListOfShows();
        resp = response;
        listOfShows = JsonConverter.mapToShows(response);
        
        response = handler.getUnwatchedEpisodes();
        List<Episode> episodes = JsonConverter.mapToEpisodes(response);
        
        List<List<Show>> list = ListOfShowsChanger.makeListOfShowTypes(listOfShows);
        
        listOfWatchingShows = list.get(0);
        /*Extended functional*/
//        listOfLaterShows = list.get(1);
//        listOfCancelledShows = list.get(2);
//        listOfRemoveShows = list.get(3);
//        list.clear();
        listOfWatchingShows = ListOfShowsChanger.makeShowsWithEpisodes(listOfWatchingShows, episodes);
        listOfWatchingShows = ListOfShowsChanger.addRefToEpisodes(listOfWatchingShows);
        
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
        setPassword("spring@Project");
        setLogin("springProject");
    }

    /**
     * This function create a List of TopShows where positions are ordered by rating
     */
    public void makeListOfTopAllShows()
    {
        String json = handler.showTopAllShows();
        listOfTopAllShows = JsonConverter.mapToTopShows(json);
    }

    /**
     * @param ShowId
     */
    public void makeListOfViewedSeries(int ShowId)
    {
        viewedSeries = handler.getListOfViewedSeries(ShowId);
    }

    /**
     * This function change rate of chosen show
     * @param rateEvent 
     */
    public void manageShowRate(RateEvent rateEvent)
    {
        try
        {
            String showId = rateEvent.getComponent().getAttributes().get("converterMessage").toString();
            int rate = ((Double) rateEvent.getRating()).intValue();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Manage show rate", "You rated:" + rate);
            handler.manageShowRate(showId, rate);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception ex)
        {
            LOG.error("Can't manage rate", ex);
        }
    }

    /**
     * 
     * @param rateEvent 
     */
    public void manageEpisodeRate(RateEvent rateEvent)
    {
        try
        {
            String episodeId = rateEvent.getComponent().getAttributes().get("converterMessage").toString();
            int rate = ((Double) rateEvent.getRating()).intValue();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Manage episode rate", "You rated:" + rate);
            handler.manageShowRate(episodeId, rate);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception ex)
        {
            LOG.error("Can't manage episode rate", ex);
            
        }
    }

    /**
     * checkEpisode function marks episode as watched
     * @param event 
     */
    public void checkEpisode(AjaxBehaviorEvent event)
    {
        try
        {
            String id = event.getComponent().getAttributes().get("value").toString();
            String title = event.getComponent().getAttributes().get("dir").toString();
            handler.checkEpisode(id);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "You watched " + title, "Thank you");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception ex)
        {
            LOG.error("Can't check episode", ex);
        }
    }
    
    public void redirectToActions()
    {
        if (auth == true)
        {
            try
            {
                FacesContext.getCurrentInstance().getExternalContext().redirect("faces/actions.xhtml");
            } catch (IOException ex)
            {
                LOG.error("Cant redirect logged ", ex);
            }
        }
    }

    /**
     * This function for sorting List of Episodes
     * @param firstObj
     * @param secondObj
     * @return 
     */
    public int mySort(Object firstObj, Object secondObj)
    {
        
        String[] first = firstObj.toString().split(" ");
        String[] second = secondObj.toString().split(" ");
        
        int firstSeason = Integer.valueOf(first[0]);
        int firstEpisode = Integer.valueOf(first[1]);
        
        int secondSeason = Integer.valueOf(second[0]);
        int secondEpisode = Integer.valueOf(second[1]);
        
        if (firstSeason > secondSeason)
        {
            return -1;
        }
        if (firstSeason < secondSeason)
        {
            return 1;
        }
        if (firstSeason == secondSeason)
        {
            if (firstEpisode > secondEpisode)
            {
                return -1;
            } else
            {
                return 1;
            }
        }
        return 0;
    }
    /**
     *
     * @param loginCookie
     * @param passwCokie
     * @return
     */
    //This function should be started on first page onload event, but I don't know how to do it
    //perhaps parameters should be request or/and response
    /*
    public void checkCookies(Cookie loginCookie, Cookie passwCookie)
    {
    String redirectTo = "";
    String userLogin = loginCookie.getValue();
    String userPassw = passwCookie.getValue();
    
    boolean isExist = checker.isUserExistInBase(userLogin, userPassw);
    if (isExist)
    {
    try
    {
    this.login = userLogin;
    this.password = userPassw;
    redirectTo = "actions";
    FacesContext.getCurrentInstance().getExternalContext().dispatch(redirectTo);
    
    } catch (IOException ex)
    {
    LOG.error("can't redirect to actions.xhtml", ex);
    }
    } else
    {
    try
    {
    FacesContext.getCurrentInstance().getExternalContext().dispatch(redirectTo);
    } catch (IOException ex)
    {
    LOG.error("Can't redirect to index.xtml. User isn't in base", ex);
    }
    }
    }*/
}
