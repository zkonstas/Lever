package LeverAPIPackage;

import twitter4j.*;

import java.util.*;

/**
 * Created by royhermann on 4/9/15.
 */
public class Result {

    public Set<User> uniqueUsers;
    public ArrayList<Status> statuses;
    private static Result instance = null;
    public int size;

    /* Constructor */

//    protected Result() {
//        // Exists only to defeat instantiation.
//    }

    public static Result getInstance() {
        if (instance == null) {
            instance = new Result();
        }
        return instance;
    }

    public Result() {
        this.uniqueUsers = new HashSet<User>();
        this.statuses = new ArrayList<>();
        this.size = 0;
    }

    public void addQueryResult(QueryResult queryResult) {
        //add all users from query result
        ArrayList users = new ArrayList();
        for (int i = 0; i < queryResult.getTweets().size(); i++) {
            users.add(queryResult.getTweets().get(i).getUser());
        }
        //add all statuses to local statuses variable
        this.statuses.addAll(queryResult.getTweets());
        this.uniqueUsers.addAll(users);
        this.size = this.statuses.size();
    }

    public int tweetCount(String word){
        int count=0;
        for(Status status : this.statuses)
        {
            if (status.getText().toLowerCase().contains(word.toLowerCase()))
                count++;
        }
        return count;
    }





    public void addStatuses(List<Status> statuses) {
        this.statuses.addAll(statuses);
        this.size = this.statuses.size();
    }

    @Override
    public String toString() {
        String retValue = "";
        for (int i = 0; i < this.statuses.size(); i++) {
            Status tweet = this.statuses.get(i);
            retValue = retValue + "@" + tweet.getUser().getScreenName() + " - " + tweet.getText() +tweet.getCreatedAt() +"\n";
        }
        return retValue;
    }


}
