package beanpackage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vlad, Roman
 */
public class JsonConverter {

    public static List<Show> mapToShows(String text){
        Gson gson = new Gson();
        Type typeOfT = new TypeToken<HashMap<String, Show>>(){}.getType();
        Map<String, Show> map = gson.fromJson(text, typeOfT);

        List<Show> list = new ArrayList<Show>();
        
        for(String st: map.keySet()){
            list.add(map.get(st));
        }
        return list;
    }

}
