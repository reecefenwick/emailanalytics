package au.com.suncorp.easyanalytics.repository;

import au.com.suncorp.easyanalytics.domain.TrackingMetadata;
import au.com.suncorp.easyanalytics.domain.TrackingReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface TrackingMetadataRepository extends JpaRepository<TrackingMetadata, UUID> {
    TrackingMetadata findByid(UUID ID);

    Set<TrackingMetadata> findBytrackingReference(TrackingReference trackingReference);
}
