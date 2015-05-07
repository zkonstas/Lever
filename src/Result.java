import twitter4j.*;
import twitter4j.User;

import java.util.*;

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

    public void addStatuses(List<Status> statuses){
        this.statuses.addAll(statuses);
    }




}
