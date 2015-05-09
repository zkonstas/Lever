

import twitter4j.Status;

import java.util.*;

public class LeverAPI {


    public static Result get(String arguments){
        return QueryManager.getResultFromArguments(arguments);
    }

    public static void graph(String barType,String title, String[] xLabels, double[] dataPoints){
        if(barType.equals("line"))
            GraphManager.createLineChart(title, xLabels, dataPoints);
        else
            GraphManager.createBarChart(title,xLabels,dataPoints);
    }

    public static void output(Object obj) {
        if (obj instanceof Status) {
            //Object is a status
            Status tweet = (Status) obj;
            System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
        }
        else{
            System.out.println(obj.toString());
        }
    }
    public static String input() {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        return s;
    }
}
