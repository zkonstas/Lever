package tests;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by edolev89 on 5/8/15.
 */
public class NewExamplesTest extends TestCase {

        @Test
        public void testMultiply() throws Exception {
            //assertEquals("10 x 0 equals 0", "", HelloWorld.e);

            //the methods to invoke today
            int[] methodsIndicesToInvoke = {1, 2};

            String[] methodIndicesToInvokeAsArgs = new String[methodsIndicesToInvoke.length];
            //convert to string array for args of OutputExamples mains
            for(int i = 0; i < methodsIndicesToInvoke.length; i++) {
                methodIndicesToInvokeAsArgs[i] = String.valueOf(methodsIndicesToInvoke[i]);
            }

            String[] expected = AbstractMainTests.executeMain("ExpectedOutputExamples", methodIndicesToInvokeAsArgs); //first param is classname


            String[] results = AbstractMainTests.executeMain("ExpectedOutputExamples", methodIndicesToInvokeAsArgs); //first param is classname


            //check if both arrays of the same size
            assertEquals(expected.length, results.length);

                for (int i = 0; i < results.length; i++) {
                    //resultsString += results[i] + "\n";
                    assertEquals(expected[i], results[i]);
                }

           // System.out.println(resultsString);
            //assertEquals(expected[0], results[0]);

        }
}
