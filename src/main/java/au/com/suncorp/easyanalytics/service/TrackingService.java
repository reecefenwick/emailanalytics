package au.com.suncorp.easyanalytics.service;

import au.com.suncorp.easyanalytics.domain.TrackingMetadata;
import au.com.suncorp.easyanalytics.domain.TrackingReference;
import au.com.suncorp.easyanalytics.repository.TrackingMetadataRepository;
import au.com.suncorp.easyanalytics.repository.TrackingReferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TrackingService {

    private static final Logger log = LoggerFactory.getLogger(TrackingService.class);

    @Autowired
    private TrackingMetadataRepository trackingMetadataRepository;

    @Autowired
    private TrackingReferenceRepository trackingReferenceRepository;

    private static final String GA_ACCOUNT_ID = "UA-69408031-1";

    public TrackingReference findTrackingRefByID(UUID trackingId){
        TrackingReference trackingRef = trackingReferenceRepository.findBytrackingID(trackingId);
        trackingRef.setTrackingMetadata(trackingMetadataRepository.findBytrackingReference(trackingRef));
        return trackingRef;
    }

    /**
     * Generate URL with embedded tracking ID
     * @return
     */
    private String generateTrackingURL(UUID trackingId) {
        return "http://www.google-analytics.com/collect" +
                "?v=1" +
                "&tid=" + GA_ACCOUNT_ID +
                "&cid=email" +
                "&t=event" +
                "&ec=email" +
                "&ea=open" +
                "&el=" + trackingId.toString() +
                "&cs=corro" +
                "&cm=email" +
                "&cn=marketingCampaign";
    }

    public List<TrackingReference> findAllTrackingRefs(String metadataQuery) {

        List<TrackingReference> trackingReferences = trackingReferenceRepository.findAll();

        List<TrackingReference> trackingRefWithMetadata = new ArrayList<>();

        for (TrackingReference trackingReference : trackingReferences) {
            Set<TrackingMetadata> trackingMetadatas = trackingMetadataRepository.findBytrackingReference(trackingReference);

            for (TrackingMetadata trackingMetadata : trackingMetadatas) {
                if (metadataQuery == null) {
                    trackingRefWithMetadata.add(trackingReference);
                } else if (trackingMetadata.getValue().toLowerCase().contains(metadataQuery.toLowerCase())) {
                    trackingRefWithMetadata.add(trackingReference);
                }
            }
        }

        return trackingRefWithMetadata;
    }

    public TrackingReference createTrackingReference(Set<TrackingMetadata> metadata) {
        TrackingReference trackingRef = new TrackingReference();

        trackingRef.setTrackingURL(generateTrackingURL(trackingRef.getTrackingID()));

        trackingReferenceRepository.save(trackingRef);

        metadata.stream().forEach(meta -> {
            meta.setTrackingReference(trackingRef);
            // This is a database call for each piece of meta
            trackingMetadataRepository.save(meta);
        });

        trackingRef.setTrackingMetadata(metadata);

        return trackingRef;
    }

    public void updateTrackingMetrics(@NotNull String trackingID, @NotNull Integer uniqueHits, @NotNull Integer totalHits) {

        try {
            UUID id = UUID.fromString(trackingID);

            TrackingReference trackingRef = trackingReferenceRepository.findBytrackingID(id);

            if (trackingRef != null) {
                log.info("Unique Hits {} and Total Hits {}", uniqueHits, totalHits);

                trackingRef.setTotalHits(totalHits);
                trackingRef.setUniqueHits(uniqueHits);

                trackingReferenceRepository.save(trackingRef);

                log.info("Updated trackingRef {}", trackingID);
            }

        } catch (IllegalArgumentException e) {
            log.error("Error converting trackingID {} with message {}", trackingID, e.getMessage());
            log.error(e.getMessage());
        }
    }
}
