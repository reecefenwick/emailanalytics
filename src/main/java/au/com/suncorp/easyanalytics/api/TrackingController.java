package au.com.suncorp.easyanalytics.api;

import au.com.suncorp.easyanalytics.api.dto.TrackingResponse;
import au.com.suncorp.easyanalytics.domain.TrackingMetadata;
import au.com.suncorp.easyanalytics.domain.TrackingReference;
import au.com.suncorp.easyanalytics.repository.TrackingMetadataRepository;
import au.com.suncorp.easyanalytics.repository.TrackingReferenceRepository;
import au.com.suncorp.easyanalytics.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/tracking")
public final class TrackingController {

    @Autowired
    private TrackingReferenceRepository trackingReferenceRepository;

    @Autowired
    private TrackingMetadataRepository trackingMetadataRepository;

    @Autowired
    private TrackingService trackingService;

    @RequestMapping(method = POST)
    public ResponseEntity<TrackingResponse> createTrackingReference(@RequestBody Set<TrackingMetadata> metadata) {
        TrackingReference trackingRef = trackingService.createTrackingReference(metadata);

        String trackingURL = trackingService.generateTrackingURL(trackingRef.getTrackingID());

        return new ResponseEntity<>(new TrackingResponse(trackingURL), HttpStatus.CREATED);
    }
}
