import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by edolev89 on 5/8/15.
 */
public class ExpectedOutputExamples {
    public static void main(String[] args) {

        for(String methodIdxString : args) {
            try {
                java.lang.reflect.Method method = ExpectedOutputExamples.class.getMethod("newExamples" + methodIdxString);
                method.invoke(null, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.out.println("No such method: newExamples" + methodIdxString);
            }
        }
//        newExamples1(); //simple user test
//        newExamples2(); //simple hashtag test
//        newExamples3(); //simple hashtag + location map test
    }

    /**
     * simple user examples
     * @return
     */
    public static Result newExamples1(){
        ArrayList<Object> al = new ArrayList<>();
        al.add("@realmadrid");
        al.add("@barcelona");
        Result result = QueryManager.getResultFromArguments(al);
        System.out.println(result);

        return result;
    }

    /**
     * simple hashtag example
     * @return
     */
    public static Result newExamples2(){
        ArrayList<Object> al = new ArrayList<>();
        al.add("#royalbaby");
        Result result = QueryManager.getResultFromArguments(al);
        System.out.println(result);

        return result;
    }

    /**
     * hashtag + location examples
     * @return
     */
    public static Result newExamples3(){
        ArrayList<Object> al = new ArrayList<>();
        al.add("#comedy");
        al.add("#nyc");
        HashMap map = new HashMap();
        map.put("location", "new york");
        al.add(map);
        Result result = QueryManager.getResultFromArguments(al);
        System.out.println(result);

        return result;
    }
}
