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
import java.lang.reflect.Array;
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
    QueryResult queryResult;
    Result customResult;
    int numberOfPages;
    FilterQuery filterQuery;


    /**
     * Class Constructor
     */
    public QueryManager() {
        this.queryString = "";
        this.userList = new ArrayList<String>();
        this.topicList = new ArrayList<String>();
        this.generalStringList = new ArrayList<String>();
        this.masterQuery = new Query();
        this.customResult = null;
        this.numberOfPages = 1;
        this.filterQuery = new FilterQuery();

    }

    public static ArrayList<Object> arrayListFromStringArguments(String arguments) {
        ArrayList<Object> al = new ArrayList<>();
        int lastCloseBracket = arguments.lastIndexOf("]");
        int lastOpenBracket = arguments.lastIndexOf("[");
        //1. Remove all maps
        while (lastCloseBracket != -1 && lastOpenBracket != -1) {
            String s = arguments.substring(lastOpenBracket+1, lastCloseBracket); //create string of just map/array
            if(s.contains(":")) { //it is a map/dictionary
                Map map = new HashMap();
                String[] pairs = s.split(",");
                for (int i = 0; i < pairs.length; i++) {
                    String[] pair = pairs[i].split(":");
                    map.put(pair[0], pair[1]);
                }
                al.add(map);
            }
            else{ //it is an array/list
                ArrayList ar = new ArrayList();
                String [] elements = s.split(",");
                for(int i=0;i<elements.length;i++)
                    ar.add(elements[i]);
            }
            //remove from string
            arguments = arguments.substring(0, lastOpenBracket) + arguments.substring(lastCloseBracket+2);
            lastCloseBracket = arguments.lastIndexOf("]");
            lastOpenBracket = arguments.lastIndexOf("[");
        }
        String[] ars = arguments.split(",");
        for (String str : ars) {
            al.add(str);
        }

        return al;
    }

    /**
     * Add a string composed of multiple paramters to the query
     *
     * @param arguments arguments fed over from lever which should be to the query
     * @return A Result containing the basic and vital information from the search query
     */
    public static Result getResultFromArguments(String arguments) {
        Twitter twitter = TwitterFactory.getSingleton();

        Query query = new Query();
        ArrayList<Object> args = QueryManager.arrayListFromStringArguments(arguments);
        QueryManager.getQueryStringForAllParamaters(args, query);
        QueryResult queryResult;
        Result result = new Result();

        //start Twitter querying
        long minTweet = 0;
        int numberOfPages = 1;
        for (int i = 0; i < 1; i++) { //default go for 5 queries, aka 500 tweets total
            try {
                if (i != 0)
                    query.setMaxId(minTweet); //for paging multiple queries together
                queryResult = twitter.search(query);

                result.addQueryResult(queryResult);
                List<Status> tweets = queryResult.getTweets();
                for (Status tweet : tweets) {
//                    output(tweet);
                    minTweet = tweet.getId();
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Adds a single string to the query
     *
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
     *
     * @param userName The username to add to the query parameters
     */
    public void addFromUser(String userName) {
        if (userName.substring(0, 1) == "@")
            userName = userName.substring(1);
        this.userList.add("from:" + userName);
    }

    /**
     * Adds a parameter to the search that will return tweets about a specific topic
     *
     * @param topic The topic to add to the query parameters
     */
    public void addTopic(String topic) {
        this.userList.add(topic);
    }

    public void addGeneralSearchString(String searchString) {
        this.generalStringList.add(searchString);
    }

    /**
     * Outputs and returns any object to the console
     *
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


        this.masterQuery.setQuery(queryString);

        long minTweet = 0;
        for (int i = 0; i < this.numberOfPages; i++) { //default go for 5 queries, aka 500 tweets total
            try {
                if (i != 0)
                    this.masterQuery.setMaxId(minTweet); //for paging multiple queries together
                this.queryResult = twitter.search(this.masterQuery);
                this.customResult = new Result();
                this.customResult.addQueryResult(this.queryResult);
                List<Status> tweets = this.queryResult.getTweets();
                for (Status tweet : tweets) {
//                    output(tweet);
                    minTweet = tweet.getId();
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * Get coordinates for location - Need to figure out how to integrate with search
     *
     * @param location - The location that you want to search for
     * @throws Exception
     */
    public static GeoLocation sendGetForLocation(String location) throws Exception {

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
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(response.toString(), map.getClass());
        ArrayList<Object> list = (ArrayList<Object>) map.get("results");
        Map<String, Object> basicInfo = (Map<String, Object>) list.get(0);
        Map<String, Object> locationMap = (Map<String, Object>) ((Map<String, Object>) basicInfo.get("geometry")).get("location");


        HashMap<String, Double> retMap = new HashMap<String, Double>();
        double lat = Double.parseDouble(locationMap.get("lat").toString());
        double longitude = Double.parseDouble(locationMap.get("lng").toString());
        double[] coordinates = new double[2];
        coordinates[0] = lat;
        coordinates[1] = longitude;


        System.out.println(lat + " " + longitude);

//        double lat1 = lat - .25;
//        double longitude1 = longitude - .25;
//        double lat2 = lat + .25;
//        double longitude2 = longitude + .25;
//        double[][] bb= {{lat1,longitude1}, {lat2,longitude2}};
//        System.out.println(bb[0][0]+","+bb[0][1]+"\n"+bb[1][0]+","+bb[1][1]);
//        this.filterQuery.locations(bb);

        GeoLocation geoLocation = new GeoLocation(lat, longitude);

        return geoLocation;

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

    private static String getQueryStringForAllParamaters(ArrayList<Object> arguments, Query query) {
        String str = "";
        /* Users */
        ArrayList userList = new ArrayList();
        ArrayList topicList = new ArrayList();
        ArrayList generalStringList = new ArrayList();

        //add into relevant groups
        for (int i = 0; i < arguments.size(); i++) {
            Object a = arguments.get(i);
            //if it is a string
            if (a instanceof String) {
                String string = (String) a;
                if (string.charAt(i) == '@')
                    userList.add(string);
                else if (string.charAt(i) == '#')
                    topicList.add(string);
                else
                    generalStringList.add(string);
            }
            if (a instanceof Map) {
                Map map = (HashMap) a;
                if (map.get("location") != null) {
                    String location = (String) map.get("location");
                    try {
                        query.setGeoCode(QueryManager.sendGetForLocation(location), 50, Query.Unit.km);
                    } catch (Exception e) {
                        System.out.println("failed getting location");
                        e.printStackTrace();
                    }

                }
            }

        }


        //Create query string
        for (int i = 0; i < userList.size(); i++) {
            str = str + userList.get(i);
            if (i < userList.size() - 1 && userList.size() > 1)
                str = str + " OR ";
        }
        /* Topics/Hashtags */
        for (int i = 0; i < topicList.size(); i++) {
//
//            if (str.substring(str.length() - 1) == "")
//                str = str + " ";
            str = str + topicList.get(i) + " ";
        }
        /* other search paramters */
        for (int i = 0; i < generalStringList.size(); i++) {
            str = str + "\""+generalStringList.get(i)+"\"" + " ";
        }

        query.setQuery(str);

        return str;
    }

    private static String formattedUser(String user) {
        if (user.charAt(0) == '@')
            return "from:" + user.substring(1);
        else
            return "from:" + user;
    }

    public void printAllStatuses() {
        for (Status tweet : this.queryResult.getTweets())
            System.out.println(tweet.getText());
    }


}
