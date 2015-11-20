package au.com.suncorp.easyanalytics.service;


import com.google.api.services.analytics.model.GaData;
import org.junit.Before;
import org.junit.Test;

public class GoogleEventServiceTest {

    private GoogleEventService googleEventService;

    @Before
    public void setup() throws Exception {
        googleEventService = new GoogleEventService();
    }

    @Test
    public void getEventsTest() throws Exception {
        GaData results = googleEventService.getEvents();

        googleEventService.printGaData(results);
    }
}
