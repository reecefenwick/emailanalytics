package au.com.suncorp.easyanalytics.service;

import au.com.suncorp.easyanalytics.domain.TrackingMetadata;
import au.com.suncorp.easyanalytics.domain.TrackingReference;
import au.com.suncorp.easyanalytics.repository.TrackingMetadataRepository;
import au.com.suncorp.easyanalytics.repository.TrackingReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class TrackingService {

    @Autowired
    private TrackingMetadataRepository trackingMetadataRepository;

    @Autowired
    private TrackingReferenceRepository trackingReferenceRepository;

    private static final String GA_ACCOUNT_ID = "UA-69408031-1";

    /**
     * Generate URL with embedded tracking ID
     * @return
     */
    public String generateTrackingURL(UUID trackingId) {
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

    public TrackingReference createTrackingReference(Set<TrackingMetadata> metadata) {
        TrackingReference trackingRef = new TrackingReference();

        trackingReferenceRepository.save(trackingRef);

        metadata.stream().forEach(meta -> {
            meta.setTrackingReference(trackingRef);
            // This is a database call for each piece of meta
            trackingMetadataRepository.save(meta);
        });

        trackingRef.setTrackingMetadata(metadata);

        return trackingRef;
    }
}
