/**
 * Created by royhermann on 4/9/15.
 */

import twitter4j.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // The factory instance is re-useable and thread safe.



        //Sample Twitter Code
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query("source:twitter4j yusukey");
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        for (Status status : result.getTweets()) {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }

    }
}
