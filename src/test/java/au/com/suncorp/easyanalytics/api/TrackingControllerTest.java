package au.com.suncorp.easyanalytics.api;

import au.com.suncorp.easyanalytics.EasyAnalyticsApplication;
import au.com.suncorp.easyanalytics.TestUtil;
import au.com.suncorp.easyanalytics.api.dto.TrackingRequest;
import au.com.suncorp.easyanalytics.domain.TrackingMetadata;
import au.com.suncorp.easyanalytics.repository.TrackingReferenceRepository;
import au.com.suncorp.easyanalytics.service.TrackingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EasyAnalyticsApplication.class)
@WebAppConfiguration
@IntegrationTest
public class TrackingControllerTest {

    @Autowired
    private TrackingReferenceRepository trackingReferenceRepository;

    @Autowired
    private TrackingService trackingService;

    private MockMvc restUploadMockMvc;

    @Before
    public void setup() {
        TrackingController trackingController = new TrackingController();
        ReflectionTestUtils.setField(trackingController, "trackingService", trackingService);
        this.restUploadMockMvc = MockMvcBuilders.standaloneSetup(trackingController).build();
    }

    @Test
    public void testCreateNewUpload() throws Exception {
        Set<TrackingMetadata> trackingMetadataList = new HashSet<>();

        TrackingMetadata trackingMetadata = new TrackingMetadata();
        trackingMetadata.setKey("email");
        trackingMetadata.setValue("reecefenwick@domain.com.au");

        trackingMetadataList.add(trackingMetadata);

        TrackingRequest trackingRequest = new TrackingRequest();
        trackingRequest.setMetadata(trackingMetadataList);

        restUploadMockMvc.perform(
                post("/api/tracking")
                        .content(TestUtil.convertObjectToJsonBytes(trackingRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.trackingURL").exists())
                .andExpect(jsonPath("$.trackingID").exists())
                .andExpect(jsonPath("$.trackingMetadata").exists());

        String TRACKING_ID = trackingReferenceRepository.findAll().get(0).getTrackingID().toString();

        // TODO - This is bad
        restUploadMockMvc.perform(
                get("/api/tracking/" + TRACKING_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackingURL").exists())
                .andExpect(jsonPath("$.trackingID").exists());

        restUploadMockMvc.perform(
                get("/api/tracking/?query=reecefenwick"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trackingURL").exists())
                .andExpect(jsonPath("$[0].trackingID").exists());
    }
}
