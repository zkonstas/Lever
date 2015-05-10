package LeverAPIPackage;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;

public class LeverAPI {


    public static Result get(String arguments) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("RGfB6zq6IYMlMgSGRmbyRSUb9")
                .setOAuthConsumerSecret("UuhAMk979kuEj5t1dXXXb4UDA6T8SKYex0oxDGGm0CjNbIJVCJ")
                .setOAuthAccessToken("247632815-9ur4aRhlfB5TdfFSgH30MDaaFq6PgVfa55AmJGAK")
                .setOAuthAccessTokenSecret("pjVCNc1xbAGE326lLSrGN2tNXKTi3zbdc6NRIgen1PjqU");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return QueryManager.getResultFromArguments(arguments);
    }

    public static void graph(String barType, String title, String[] xLabels, double[] dataPoints) {
        if (barType.equals("line"))
            GraphManager.createLineChart(title, xLabels, dataPoints);
        else
            GraphManager.createBarChart(title, xLabels, dataPoints);
    }

    public static void output(Object obj) {
        if (obj instanceof Status) {
            //Object is a status
            Status tweet = (Status) obj;
            System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
        } else {
            System.out.println(obj.toString());
        }
    }

    public static String input() {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        return s;
    }
}
