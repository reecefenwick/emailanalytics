package au.com.suncorp.easyanalytics.api;

import au.com.suncorp.easyanalytics.api.dto.TrackingRequest;
import au.com.suncorp.easyanalytics.api.dto.TrackingResponse;
import au.com.suncorp.easyanalytics.domain.TrackingReference;
import au.com.suncorp.easyanalytics.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class TrackingWSController {

    @Autowired
    private TrackingService trackingService;

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTrackingURL")
    @ResponsePayload
    public TrackingResponse getTrackingURL(@RequestPayload TrackingRequest request) {
        TrackingReference trackingRef = trackingService.createTrackingReference(request.getMetadata());

        String trackingURL = trackingService.generateTrackingURL(trackingRef.getTrackingID());

        return new TrackingResponse(trackingURL);
//        return new ResponseEntity<>(new TrackingResponse(trackingURL), HttpStatus.CREATED);
    }

}
