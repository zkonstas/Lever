import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by royhermann on 5/7/15.
 */
public class LeverTests {

    @Test
    /**
     * Test getting coordinates for a location
     */
    public void testCoordinates() throws Exception {
        QueryManager qm = new QueryManager();
         Map<String,String> coordinates = qm.sendGetForLocation("new york");
        assertEquals(coordinates.get("lat"),"40.7127837");
        assertEquals(coordinates.get("long"),"-74.0059413");
    }

}