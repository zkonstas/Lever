/**
 * Created by royhermann on 4/9/15.
 */

import sun.jvm.hotspot.utilities.Interval;
import twitter4j.*;
import twitter4j.User;

import java.util.*;


public class Main {

    public static void main(String[] args) {


        newExamples1();
//        example1();// simple user query
//        example2(); // simple hashtag query
//        example3();//simple location query
//        example4();//query with bar graph
//        example5();//query with line graph
//        example6();//sentiment analysis
//        GraphManager gm = new GraphManager();
//        gm.createLineChart(new double[]{120,200,50});



    }

    public static void newExamples1(){
        ArrayList<Object> al = new ArrayList<>();
        al.add("#comedy");
        al.add("#nyc");
        HashMap map = new HashMap();
        map.put("location","new york");
        al.add(map);
        Result result = QueryManager.getResultFromArguments(al);
        System.out.println(result);
    }

    /**
     * Here we run a sample query fetching all tweets from two users
     */
    public static void example1() {

        QueryManager qm = new QueryManager();
        qm.masterQuery.setCount(100);
        qm.addFromUser("realmadrid");
        qm.addFromUser("fcbarcelona");
        qm.get();
    }

    /**
     * Here we run a sample query fetching all tweets about a certain topic
     */
    public static void example2() {
        QueryManager qm = new QueryManager();
        qm.masterQuery.setCount(10);
        qm.addTopic("#hackdisrupt");
        qm.get();
        qm.printAllStatuses();
    }

    /**
     * Here we run a sample query fetching all tweets in a certain location
     */
    public static void example3(){
        QueryManager qm = new QueryManager();
        qm.masterQuery.setCount(10);
        try {
            qm.sendGetForLocation("Israel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        qm.get();
        qm.printAllStatuses();

    }

    /**
     * Here we run a sample query fetching all tweets from two users.
     * Then we compare the number of them that have messi vs ronaldo, and draw it on a graph
     */
    public static void example4() {

//        GraphManager gm = new GraphManager();
//        gm.createBarChart("messi vs ronaldo mentions", "who", "# of mentions",new String[]{"Messi","Ronaldo"},new String[]{"0",String.valueOf(20)},new double[]{15,5});
//        return;

        QueryManager qm = new QueryManager();
        qm.masterQuery.setCount(100);
        qm.addFromUser("realmadrid");
        qm.addFromUser("fcbarcelona");
        qm.get();

        //received resuts
        int messi = 0;
        int ronaldo = 0;
        for (Status status : qm.customResult.statuses) {
            if (status.getText().toLowerCase().contains("messi"))
                messi++;
            if (status.getText().toLowerCase().contains("ronaldo"))
                ronaldo++;
        }

        int max = (messi > ronaldo) ? messi : ronaldo;


        GraphManager gm = new GraphManager();
        gm.createBarChart("messi vs ronaldo mentions", "who", "# of mentions", new String[]{"Messi", "Ronaldo"}, null, new double[]{messi, ronaldo});

    }

    /**
     * This example searches for all tweets about england elections and compares their time distribution before and after a certain time
     */
    public static void example5() {

//        GraphManager gm = new GraphManager();
//        gm.createBarChart("messi vs ronaldo mentions", "who", "# of mentions",new String[]{"Messi","Ronaldo"},new String[]{"0",String.valueOf(20)},new double[]{15,5});
//        return;

        QueryManager qm = new QueryManager();
        qm.masterQuery.setCount(100);
        qm.addGeneralSearchString("england elections");
        qm.get();

        //received resuts
        Date bufferDate;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(Calendar.YEAR, Calendar.MONTH, 7, 12, 0, 0);
        bufferDate = cal.getTime(); // get back a Date object

        int before = 0;
        int after = 0;
        for (Status status : qm.customResult.statuses) {
            Date statusDate = status.getCreatedAt();
            Interval interval = new Interval(statusDate, bufferDate); //for time difference
            if (statusDate.compareTo(bufferDate) < 0) {
                before++;
            } else after++;
        }


        GraphManager gm = new GraphManager();
        gm.createBarChart("UK Elections Tweet Metrics", "Time", "Volume", new String[]{"Before 12:00:00PM", "After Before 12:00:00PM"}, new String[]{"0", "100"}, new double[]{before,after});

    }

    /**
     * Sentiment analysis - not working yet
     */
    public static void example6() {
        QueryManager qm = new QueryManager();
        qm.masterQuery.setCount(10);
        qm.addGeneralSearchString("england :(");
        qm.get();
        System.out.println(qm.customResult.getSize());
        for(int i=0;i<qm.customResult.getSize();i++)
            System.out.println(qm.customResult.statuses.get(i).getText());
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
