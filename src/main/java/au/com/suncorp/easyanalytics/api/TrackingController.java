package au.com.suncorp.easyanalytics.api;

import au.com.suncorp.easyanalytics.api.dto.TrackingURLDTO;
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

import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<TrackingURLDTO> createTrackingReference(@RequestBody Set<TrackingMetadata> metadata,
                                                                  HttpServletResponse response) {
        TrackingReference trackingRef = new TrackingReference();

        trackingReferenceRepository.save(trackingRef);

        metadata.stream().forEach(meta -> {
            meta.setTrackingReference(trackingRef);
            // This is a database call for each piece of meta
            trackingMetadataRepository.save(meta);
        });

        trackingRef.setTrackingMetadata(metadata);

        String trackingUrl = trackingService.generateTrackingURL(trackingRef.getTrackingID());

        return new ResponseEntity<>(new TrackingURLDTO(trackingUrl), HttpStatus.CREATED);
    }
}
