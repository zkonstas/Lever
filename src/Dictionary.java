import java.util.HashMap;

/**
 * Created by royhermann on 5/7/15.
 */
public class Dictionary {

    private HashMap<Object, Object> map;

    public Dictionary() {
        map = new HashMap<>();
    }

    public void addObject(String str) {
        String[] arr = str.split(":");
        if (arr.length == 2)
            map.put(arr[0], arr[1]);
    }

    public Object get(String key){
        return map.get(key);
    }
}
