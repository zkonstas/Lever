package LeverAPIPackage;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;

public class LeverAPI {


    public static Result get(String arguments) {
        return QueryManager.getResultFromArguments(arguments);
    }

    public static void graph(String barType, String title, ArrayList<String> xLabels, ArrayList dataArray) {
        String[] xLabelsArray = xLabels.toArray(new String[xLabels.size()]);
        if (barType.equals("line"))
            GraphManager.createLineChart(title, xLabelsArray, dataArray);
        else
            GraphManager.createBarChart(title, xLabelsArray, dataArray);
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
