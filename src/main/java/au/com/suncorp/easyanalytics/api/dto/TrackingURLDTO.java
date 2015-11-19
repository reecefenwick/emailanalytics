package au.com.suncorp.easyanalytics.api.dto;

public class TrackingURLDTO {
    private String trackingUrl;

    public TrackingURLDTO(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }
}
