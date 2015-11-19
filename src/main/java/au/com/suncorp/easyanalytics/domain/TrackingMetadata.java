package au.com.suncorp.easyanalytics.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "TRACKING_METADATA")
public class TrackingMetadata {

    @Id
    @NotNull
    @Column(name = "ID")
    private UUID id;

    @ManyToOne
    @NotNull
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private TrackingReference trackingReference;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;

    public TrackingMetadata() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TrackingReference getTrackingReference() {
        return trackingReference;
    }

    public void setTrackingReference(TrackingReference trackingReference) {
        this.trackingReference = trackingReference;
    }
}
