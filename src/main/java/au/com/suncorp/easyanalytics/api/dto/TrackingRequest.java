package au.com.suncorp.easyanalytics.api.dto;

import au.com.suncorp.easyanalytics.domain.TrackingMetadata;

import java.util.Set;

public class TrackingRequest {

    private Set<TrackingMetadata> metadata;

    public Set<TrackingMetadata> getMetadata() {
        return metadata;
    }

    public void setMetadata(Set<TrackingMetadata> metadata) {
        this.metadata = metadata;
    }
}
