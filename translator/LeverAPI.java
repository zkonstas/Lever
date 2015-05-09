import java.util.*;

public class LeverAPI {


	public static Result get(String arguments){
		return QueryManager.getResultFromArguments(str);
	}

	public static void createBarGraph(String title, String[] xLabels, double[] dataPoints){
		GraphManager.createBarChart(title,xLabels,dataPoints);
	}
	public static void createLineGraph(String title, String[] xLabels, double[] dataPoints){
		GraphManager.createLineChart(title, xLabels, dataPoints);
	}

	public static void output(Object obj) {
		if (obj instanceof Status) {
			//Object is a status
			Status tweet = (Status) obj;
			System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
		} else if (obj instanceof User) {
			User user = (User) obj;
			System.out.println("@" + user.getScreenName());
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
