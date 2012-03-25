package org.ncmipt.miptshows;

import org.ncmipt.miptshows.api.Show;
import org.ncmipt.miptshows.api.ConnectionManager;
import org.ncmipt.miptshows.api.JsonConverter;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Vlad, Roman
 */
@ManagedBean
@SessionScoped
public class ActionBean {

    private String response;
    private String login = "";
    private String password = "";
    private int status;
    private int rating;

    private List<Show> listOfShows;
    private ConnectionManager handler;


    // Block of getters & setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
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
    // End of the block


    public ActionBean() {
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
        if(status == 200)
        {
            redirectTo = "actions";
        }
        else
        {
            FacesMessage fm = new FacesMessage("Authorization fails");
            FacesContext.getCurrentInstance().addMessage("Authorization fails", fm);
            redirectTo = "";
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
}