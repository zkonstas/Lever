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

    public Date[] getTimesOfAllTweets() {
        Date[] times = new Date[this.statuses.size()];
        int i = 0;
        for (Status status : this.statuses) {
            times[i] = status.getCreatedAt();
        }
        return times;
    }

    public int getSize() {
        return this.statuses.size();
    }

    public double getFetchTime() {
//        return this.origResult.getCompletedIn();
        return 0.0;
    }


//    /* create a data structure with bin information in regards to number of tweets per time slot */
//    public HashMap<Date,Integer> getDistributionByMinuteForTime(Date time){
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(time);
//        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//        int minutes = calendar.get(Calendar.MINUTE);
//        HashMap<Date,Integer> timeBins = new HashMap<Date,Integer>();
//        int[] amounts = new int[60];
//        for(Status status : this.statuses){
//            calendar = Calendar.getInstance();
//            calendar.setTime(status.getCreatedAt());
//            int h = calendar.get(Calendar.HOUR_OF_DAY);
//            int m = calendar.get(Calendar.MINUTE);
//            long diff = time.getTime() - status.getCreatedAt().getTime();
//        }
//        return amounts;
//    }

    public void addStatuses(List<Status> statuses) {
        this.statuses.addAll(statuses);
    }

    @Override
    public String toString() {
        String retValue = "";
        for (int i = 0; i < this.statuses.size(); i++) {
            Status tweet = this.statuses.get(i);
            retValue = retValue + "@" + tweet.getUser().getScreenName() + " - " + tweet.getText() + "\n";
        }
        return retValue;
    }


}
