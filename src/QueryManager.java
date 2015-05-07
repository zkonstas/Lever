import com.google.gson.Gson;
import scala.util.parsing.combinator.testing.Str;
import sun.lwawt.macosx.CWrapper;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.User;
import twitter4j.*;


import javax.xml.bind.SchemaOutputResolver;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


/**
 * Created by royhermann on 4/17/15.
 */
public class QueryManager {

    Query masterQuery;
    String queryString;
    ArrayList<String> userList;
    ArrayList<String> topicList;
    ArrayList<String> generalStringList;
    GeoLocation geoCoordinates;
    QueryResult queryResult;
    Result customResult;



    /**
     * Class Constructor
     */
    public QueryManager() {
        this.queryString = "";
        this.userList = new ArrayList<String>();
        this.topicList = new ArrayList<String>();
        this.generalStringList = new ArrayList<String>();
        this.customResult = null;
        geoCoordinates = null;

    }

    /**
     * Add a string composed of multiple paramters to the query
     * @param queryString The string to add to the search
     * @return Formatted string matching query restrictions
     */
    public String queryFromString(String queryString) {
        String returnString = "";
        String[] paramters = queryString.split(" ");

        //Format the string to match query restrictions
        /* NOT IMPLEMENTED YET */
        for(String str : paramters)


        System.out.println(paramters);
        return returnString;

    }

    /**
     * Adds a single string to the query
     * @param queryString The specific string to add to the query
     */
    public void addStringToQuery(String queryString) {
        //check if user
        if (queryString.charAt(0) == '@')
            addFromUser(queryString);
        else if (queryString.charAt(0) == '#')
            addTopic(queryString);
        else if (queryString.charAt(0) != '@' && queryString.charAt(0) != '#')
            addGeneralSearchString(queryString);

    }

    /**
     * Adds a parameter to the search that will return tweets composed by a specific user
     * @param userName The username to add to the query parameters
     */
    public void addFromUser(String userName) {
        if (userName.substring(0, 1) == "@")
            userName = userName.substring(1);
        this.userList.add("from:" + userName);
    }

    /**
     * Adds a parameter to the search that will return tweets about a specific topic
     * @param topic The topic to add to the query parameters
     */
    public void addTopic(String topic) {
        this.userList.add(topic);
    }

    public void addGeneralSearchString(String searchString) {
        this.generalStringList.add(searchString);
    }

    /**
     * Outputs any object to the console
     * @param object The object to output to transfer to a string representation
     * @return The string representation of the objet paramater
     */
    public String output(Object object) {
        String retValue = "";
        if (object instanceof Status) {
            //Status object
            Status tweet = (Status) object;
            retValue = ("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
        } else if (object instanceof User) {
            //User object
            User user = (User) object;
            retValue = ("@" + user.getScreenName());
        } else {
            //Non object
            retValue = object.toString();
        }
        System.out.println(retValue);
        return retValue;
    }

    /**
     * Runs the search query
     */
    public void get() {
        Twitter twitter = TwitterFactory.getSingleton();
        //construct string for query


        String queryString = this.getQueryStringForAllParamaters();
        this.output("query string = " + queryString);


        this.masterQuery = new Query(queryString);
        this.masterQuery.setCount(1000);
        //Add coordinates to search if there exists such a restriction
        if(geoCoordinates != null)
            this.masterQuery.geoCode(geoCoordinates, 100, "150 km");

        try {
            this.queryResult = twitter.search(this.masterQuery);
            List<Status> tweets = this.queryResult.getTweets();
            for (Status tweet : tweets) {
                output(tweet);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get coordinates for location - Need to figure out how to integrate with search
     * @param location - The location that you want to search for
     * @throws Exception
     */
    public Map<String,String> sendGetForLocation(String location) throws Exception {

        //Replace spaces with '+' char for web query
        location = location.replace(" ", "+");
        String url = "http://maps.google.com/maps/api/geocode/json?address=" + location;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map= gson.fromJson(response.toString(), map.getClass());
        ArrayList<Object> list = (ArrayList<Object>) map.get("results");
        Map <String,Object> basicInfo = (Map<String,Object>)list.get(0);
        Map <String,Object> locationMap = (Map<String,Object>)((Map<String,Object>)basicInfo.get("geometry")).get("location");



        HashMap<String,String> retMap = new HashMap<String, String>();
        retMap.put("lat", locationMap.get("lat").toString());
        retMap.put("long", locationMap.get("lng").toString());

//        System.out.println(locationMap.get("lat")+"    "+locationMap.get("lng"));

        return retMap;

    }

    private String getQueryStringForAllParamaters() {
        String str = "";
        /* Users */
        for (int i = 0; i < this.userList.size(); i++) {
            str = str + this.userList.get(i);
            if (i < this.userList.size() - 1)
                str = str + " OR ";
        }
        /* Topics/Hashtags */
        for (int i = 0; i < this.topicList.size(); i++) {
            if (str.substring(str.length() - 1) == "")
                str = str + " ";
            str = str + this.topicList.get(i);
        }
        /* other search paramters */
        for (int i = 0; i < this.generalStringList.size(); i++) {
            str = str + this.generalStringList.get(i);
        }

        return str;
    }


    public void graphByTime() {

    }


}
