package project.service;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RouteInfoTest {

    private String jsonString = "{\n" +
            "   \"destination_addresses\" : [ \"Leiden, Netherlands\" ],\n" +
            "   \"origin_addresses\" : [ \"Mekelweg 5, 2628 CD Delft, Netherlands\" ],\n" +
            "   \"rows\" : [\n" +
            "      {\n" +
            "         \"elements\" : [\n" +
            "            {\n" +
            "               \"distance\" : {\n" +
            "                  \"text\" : \"24.2 km\",\n" +
            "                  \"value\" : 24187\n" +
            "               },\n" +
            "               \"duration\" : {\n" +
            "                  \"text\" : \"26 mins\",\n" +
            "                  \"value\" : 1579\n" +
            "               },\n" +
            "               \"status\" : \"OK\"\n" +
            "            }\n" +
            "         ]\n" +
            "      }\n" +
            "   ],\n" +
            "   \"status\" : \"OK\"\n" +
            "}";

    private RouteInfo routeInfo;

    @Test
    public void parseJsonToRouteObjTest() {
        Gson gson = new Gson();
        assertTrue(gson.fromJson(jsonString, RouteInfo.class) instanceof RouteInfo);
    }

    @Before
    public void initializeRouteInfoBeforeTest() {
        Gson gson = new Gson();
        routeInfo = gson.fromJson(jsonString, RouteInfo.class);
    }

    @Test
    public void getDistanceEqualsTest() {
        assertEquals(routeInfo.getDistance(), 24187);
    }

    @Test
    public void getDistanceTextEqualsTest() {
        assertEquals(routeInfo.getDistanceText(), "24.2 km");
    }

    @Test
    public void getTimeTextEqualsTest() {
        assertEquals(routeInfo.getTimeText(), "26 mins");
    }

    @Test
    public void getTimeTextNotEqualsTest() {
        assertNotEquals(routeInfo.getTimeText(), "26");
    }

    @Test
    public void getDistanceTextNotEqualsTest() {
        assertNotEquals(routeInfo.getDistanceText(), "25.2");
    }

    @Test
    public void getDistanceNotEqualsTest() {
        assertNotEquals(routeInfo.getDistance(), 25);
    }
}

