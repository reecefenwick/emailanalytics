package au.com.suncorp.easyanalytics.repository;

import au.com.suncorp.easyanalytics.domain.TrackingReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrackingReferenceRepository extends JpaRepository<TrackingReference, UUID> {
    TrackingReference findBytrackingID(UUID trackingID);
}
