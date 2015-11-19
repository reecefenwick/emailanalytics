package au.com.suncorp.easyanalytics.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TRACKERS")
public class TrackingReference {

    @Id
    @NotNull
    @Column(name = "ID")
    private UUID trackingID;

    @OneToMany(mappedBy = "trackingReference")
    private Set<TrackingMetadata> trackingMetadata = new HashSet<>();

    public TrackingReference() {
        this.trackingID = UUID.randomUUID();
    }

    public UUID getTrackingID() {
        return trackingID;
    }

    public Set<TrackingMetadata> getTrackingMetadata() {
        return trackingMetadata;
    }

    public void setTrackingMetadata(Set<TrackingMetadata> trackingMetadata) {
        this.trackingMetadata = trackingMetadata;
    }
}