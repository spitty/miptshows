package beanpackage;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
    private Handler handler;


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
    public int authorization()
    {
        listOfShows = null;
        handler = new Handler();
        status = handler.getAuthorization(login, password);
        return status;
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