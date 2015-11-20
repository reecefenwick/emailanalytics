package au.com.suncorp.easyanalytics.api.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "trackingResponse")
public class TrackingResponse {

    @XmlElement
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
