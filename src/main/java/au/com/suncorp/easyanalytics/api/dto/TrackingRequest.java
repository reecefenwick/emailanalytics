package au.com.suncorp.easyanalytics.api.dto;

import au.com.suncorp.easyanalytics.domain.TrackingMetadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "trackingRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrackingRequest {

    @XmlElement
    private Set<TrackingMetadata> metadata;

    public Set<TrackingMetadata> getMetadata() {
        return metadata;
    }

    public void setMetadata(Set<TrackingMetadata> metadata) {
        this.metadata = metadata;
    }
}
