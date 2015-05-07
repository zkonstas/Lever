import twitter4j.*;
import twitter4j.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by royhermann on 4/9/15.
 */
public class Result {

    Set<User> uniqueUsers;
    ArrayList<Status> statuses;

    /* Constructor */
    public Result(QueryResult queryResult){
        this.uniqueUsers = new HashSet<User>();
        this.statuses = (ArrayList<Status>)queryResult.getTweets();
    }

    public Date[] getTimesOfAllTweets(){
        Date [] times = new Date[this.statuses.size()];
        int i = 0;
        for(Status status : this.statuses){
            times[i] = status.getCreatedAt();
        }
        return times;
    }




}
