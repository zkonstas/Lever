public class LeverAPI {
	public static void output(String s) {
		if (obj instanceof String) {
			System.out.println(s);
		} else if (obj instanceof Status) {
            //Object is a status
            Status tweet = (Status) obj;
            System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
        } else if (obj instanceof User) {
            User user = (User) obj;
            System.out.println("@" + user.getScreenName());
        }
	}
	public static String input() {
		Scanner in = new Scanner(System.in);
		s = in.nextLine();
		return s;
	}
}
