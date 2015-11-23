package au.com.suncorp.easyanalytics.api;

import au.com.suncorp.easyanalytics.api.dto.TrackingRequest;
import au.com.suncorp.easyanalytics.domain.TrackingReference;
import au.com.suncorp.easyanalytics.service.GoogleEventService;
import au.com.suncorp.easyanalytics.service.TrackingService;
import com.google.api.services.analytics.model.GaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/tracking")
public final class TrackingController {

    private static final Logger log = LoggerFactory.getLogger(TrackingController.class);

    @Autowired
    private GoogleEventService googleEventService;

    @Autowired
    private TrackingService trackingService;

    @RequestMapping(method = POST)
    public ResponseEntity<TrackingReference> createTrackingReference(@RequestBody TrackingRequest trackingRequst) {
        TrackingReference trackingRef = trackingService.createTrackingReference(trackingRequst.getMetadata());

        return new ResponseEntity<>(trackingRef, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{ID}")
    public ResponseEntity<?> getTrackingReference(@PathVariable String ID) {
        return new ResponseEntity<>(trackingService.findTrackingRefByID(UUID.fromString(ID)), HttpStatus.OK);
    }

    @RequestMapping
    public ResponseEntity<List<TrackingReference>> findAllTrackingReferences(@RequestParam(value = "query", required = false)
                                                                                 String query) {
        return new ResponseEntity<>(trackingService.findAllTrackingRefs(query), HttpStatus.OK);
    }

    /**
     * Scheduled task that pulls the latest info from google analytics.
     * Updates all tracking references pulled from GA
     */
    @Scheduled(fixedRate = 50000)
    public void updateTrackingDataFromGoogleAnalytics() {

        log.info("Attempting to update all tracking references");

        try {

            log.info("Retrieving results from google analytics");

            GaData results = googleEventService.getEvents();

            googleEventService.printGaData(results);

            if (results.getRows() == null || results.getRows().isEmpty()) {
                System.out.println("No results found.");
                return;
            }

            log.info("Extracting information from results");

            for (List<String> row : results.getRows()) {
                Integer currentColumn = 0;

                String trackingId = null;
                Integer totalHits = null;
                Integer uniqueHits = null;

                for (String column : row) {
                    if (currentColumn == 0) trackingId = column;
                    if (currentColumn == 1) totalHits = Integer.parseInt(column);
                    if (currentColumn == 2) uniqueHits = Integer.parseInt(column);

                    currentColumn++;
                }

                log.info("Updating tracking reference with results");

                trackingService.updateTrackingMetrics(trackingId, uniqueHits, totalHits);

                log.info("Updating analytics data complete");
            }

        } catch (Exception e) {
            log.error("There was an exception with message {} and cause {}", e.getMessage(), e.getCause());
        }
    }
}
