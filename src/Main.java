/**
 * Created by royhermann on 4/9/15.
 */

import LeverAPIPackage.*;
import sun.jvm.hotspot.utilities.Interval;
import twitter4j.*;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;


import java.util.*;


public class Main {

    public static void main(String[] args) {


//        newExample1(); //simple
        newExample2(); //UK election with graph
//        newExample3(); //UK election between cities
//        newExample4(); //UK election between cities
//        example1();// simple user query
//        example2(); // simple hashtag query
//        example3();//simple location query
//        example4();//query with bar graph
//        example5();//query with line graph
//        example6();//sentiment analysis
//        GraphManager gm = new GraphManager();
//        gm.createLineChart(new double[]{120,200,50});


    }

    /**
     * In Lever:
     * program {
     * <p/>
     * var result = get #nyc, ["location" : "new york"],#manhattan;
     * output result;
     * <p/>
     * }
     */
    public static void newExample1() {
        Result result = LeverAPI.get("#nyc,[\"location\":\"new york\",\"since\":\"2015-05-04\",\"until\":\"2015-05-07\",\"result type\":\"recent\"], #manhattan");
        LeverAPI.output(result);
    }

    /**
     * In Lever: //not sure about this, we will need to talk about it
     * program{
     * <p/>
     * var result = get "uk election";
     * var cameronCount = 0;
     * var milbandCount = 0;
     * for(int i=0;i<result.statuses.size;i++){
     * if(result.statuses.getText.contains("cameron"))
     * cameronCount = cameronCount +1;
     * if(result.statuses.getText.contains("milband"))
     * milbandCount = milbandCount+1;
     * <p/>
     * }
     * <p/>
     * Graph.barGraph("recent election tweets",["cameron","milband"],[cameronCount,milbandCount]);
     * <p/>
     * <p/>
     * }
     */
    public static void newExample2() {

        String str = "uk election,[\"since\":\"2015-05-01\",\"until\":\"2015-05-07\"];";
        QueryManager.numberOfPages = 1;
        Result result = LeverAPI.get(str);
        ArrayList al = new ArrayList(Collections.nCopies(4, 0));
        al.set(0, result.tweetCount("cameron"));
        al.set(1, result.tweetCount("Milband"));
        al.set(2, result.tweetCount("sturgeon"));
        al.set(3, result.tweetCount("clegg"));

        LeverAPI.output(result);
        LeverAPI.graph("bar", "UK Election Tweets, Candidate Mentions - May 1st - 7th", new String[]{"Cameron", "Milband", "Sturgeon", "Clegg"}, al);
    }

    public static void newExample3() {

////        String str = "uk election,[\"location\":\"london\",\"since\":\"2015-05-04\",\"until\":\"2015-05-07\"]";
//        String str = "uk election,#ukelection,[\"since\":\"2015-05-04\",\"until\":\"2015-05-07\"]";
//        QueryManager.numberOfPages = 10;
//        Result result = LeverAPI.get(str);
//        double[] counts = new double[2];
//        counts[0] = result.tweetCount("cameron");
//        counts[1] = result.tweetCount("milband");
////        LeverAPI.output(result);
//        LeverAPI.graph("bar", "UK Election Tweets - Popular Tweets 05/07-05/08", new String[]{"Cameron", "Milband"}, counts);
//        LeverAPI.output(result.getSize());

    }

    /**
     * Get tweet with most retweets
     */
    public static void newExample4() {

        String str = "uk election,#ukelection";


    }

    public static void findMostRetweetedTweet(String queryString) {
        QueryManager.numberOfPages = 1;
        Result result = LeverAPI.get(queryString);
        int maxIndex = 0;
        for (int i = 0; i < result.getSize(); i++) {
            if (result.statuses.get(maxIndex).getRetweetCount() < result.statuses.get(i).getRetweetCount())
                maxIndex = i;
        }
        Status mostReTweetedStatus = result.statuses.get(maxIndex);
        LeverAPI.output("most retweeted tweet status - @" + mostReTweetedStatus.getUser().getScreenName() + " - " + mostReTweetedStatus.getText());
        LeverAPI.output("# of retweets - " + mostReTweetedStatus.getRetweetCount());
    }


    public static void output(Object obj) {
        //Fix this eventually
        if (obj instanceof Status) {
            //Object is a status
            Status tweet = (Status) obj;
            System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
        } else if (obj instanceof User) {
            User user = (User) obj;
            System.out.println("@" + user.getScreenName());
        } else {
            System.out.println(obj.toString());
        }

    }

    public static String input() {
        int a;
        float b;
        String s;

        Scanner in = new Scanner(System.in);
        s = in.nextLine();
        return s;
    }

}
