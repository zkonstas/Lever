/**
 * Created by royhermann on 4/9/15.
 */

import twitter4j.*;
import twitter4j.User;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // The factory instance is re-useable and thread safe.


        //Sample Twitter Code
        Twitter twitter = TwitterFactory.getSingleton();
        //1. Hashtags and strings seem to work fine
        Query queryA = new Query("#superclassico");
        //2. For user searchig, add from:<username>
        Query queryB = new Query("from:realmadrid");
        //3. For multiple user, seperate by " OR "
        Query queryC = new Query("from:realmadrid OR from:barcelonafc");

        QueryResult resultA = null;
        try {
            resultA = twitter.search(queryB);
            List<Status> tweets = resultA.getTweets();
            final int LIMIT = 10; //set limit to 10
            for (int i = 0; i < tweets.size() && i < LIMIT; i++) {
                Status status = tweets.get(i);
                output(status);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        QueryResult resultB = null;
        try {
            resultB = twitter.search(queryA);
            List<Status> tweets = resultB.getTweets();
            final int LIMIT = 10; //set limit to 10
            for (int i = 0; i < tweets.size() && i < LIMIT; i++) {
                User user = tweets.get(i).getUser();
                output(user);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

    public static void queryForSomething(String something) {
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            Query query = new Query("#roy");
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {

                }
            } while ((query = result.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }

    public static void output(Object obj) {
        //Fix this eventually
//        if(obj.getClass() == Status.class){
//            //Object is a status
//            Status tweet = (Status)obj;
//            System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
//        }
//        else if(obj.getClass() == User.class){
//            User user = (User)obj;
//            System.out.println("@"+user.getScreenName());
//        }

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
