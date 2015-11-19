package au.com.suncorp.easyanalytics.service;


import org.junit.Before;
import org.junit.Test;

public class GoogleEventServiceTest {

    private GoogleEventService googleEventService = new GoogleEventService();
    @Before
    public void setup() {
    }

    @Test
    public void getEventsTest() throws Exception {
        googleEventService.getEvents();
    }
}
