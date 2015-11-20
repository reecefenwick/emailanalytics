package au.com.suncorp.easyanalytics.api.dto;

public class TrackingResponse {

    private String trackingUrl;

    public TrackingResponse(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }
}
