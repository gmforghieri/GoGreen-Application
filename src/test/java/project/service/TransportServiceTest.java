package project.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TransportServiceTest {

    @Autowired
    private TransportService transportService;

    @Test
    public void apiParserTest() {
        String url = transportService.apiParser("https:", "1", "2", "3 a b");
        assertEquals(url, "https:1&2&3 a b");
    }

    @Test
    public void apiParserWithoutKeyTest() {
        String url = transportService.apiParser("https:", "1", "2", null);
        assertEquals("https:1&2&key=AIzaSyDBHAC7HNUYpf7pxC8qLWzimyUhSOmY7xY", url);
    }

    @Test
    public void getRouteInfoTest() {
        assertTrue(transportService.getRouteInfo("delft", "den haag", "bicycling") instanceof RouteInfo);
    }

    @Test
    public void getRouteInfoExceptionTest() {
        assertEquals(null, transportService.getRouteInfo(null, null, null));
    }

    @Test
    public void co2EmissionCarTest() {
        assertEquals((int) transportService.co2EmissionUsingCar(100000), 13);
    }

    @Test
    public void co2EmissionPTTest() {
        assertEquals((int) transportService.co2EmissionUsingPT(1000000), 45);
    }

    @Test
    public void getMapLinkTest() {
        String url = transportService.getGoogleMapDirectionLink("1", "2 3", "transit");
        assertEquals(url, "https://www.google.com/maps/dir/?travelmode=transit&origin=1&destination=2+3&api=1");
    }
}
