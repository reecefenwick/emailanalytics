package au.com.suncorp.easyanalytics.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TrackingService {

    private static final String GA_ACCOUNT_ID = "UA-69408031-1";

    /**
     * Generate URL with embedded tracking ID
     * @return
     */
    public String generateTrackingURL(UUID trackingId) {
        return "http://www.google-analytics.com/collect" +
                "?v=1" +
                "&tid=" + GA_ACCOUNT_ID +
                "&cid=email" +
                "&t=event" +
                "&ec=email" +
                "&ea=open" +
                "&el=" + trackingId.toString() +
                "&cs=corro" +
                "&cm=email" +
                "&cn=marketingCampaign";
    }
}
