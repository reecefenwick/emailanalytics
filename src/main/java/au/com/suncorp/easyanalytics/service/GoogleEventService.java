package au.com.suncorp.easyanalytics.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.GaData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class GoogleEventService {

    public GoogleEventService() {

    }

    public Analytics initializeAnalytics() throws Exception {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        Collection<String> collection = new HashSet<>();
        collection.add(AnalyticsScopes.ANALYTICS_READONLY);

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .setServiceAccountId("account-1@fenwickreece08.iam.gserviceaccount.com")
                .setServiceAccountPrivateKeyFromP12File(new File("/Users/reecefenwick/gakey.p12"))
                .setServiceAccountScopes(collection)
                .build();

        return new Analytics.Builder(httpTransport, jsonFactory, credential)
            .setApplicationName("Hello Analytics")
            .build();
    }

    public void getEvents() throws Exception {
        Analytics analytics = initializeAnalytics();

//        String profileId = "112072484";
//        String profileId = "110778647";
        String profileId = "110778647";

        GaData gaData = executeDataQuery(analytics, profileId);

        printGaData(gaData);

//        analytics.data();
    }

    private static void printGaData(GaData results) {

        System.out.println(
                "printing results for profile: " + results.getProfileInfo().getProfileName());

        if (results.getRows() == null || results.getRows().isEmpty()) {
            System.out.println("No results Found.");
        } else {

            // Print column headers.
            for (GaData.ColumnHeaders header : results.getColumnHeaders()) {
                System.out.printf("%30s", header.getName());
            }
            System.out.println();

            // Print actual data.
            for (List<String> row : results.getRows()) {
                for (String column : row) {
                    System.out.printf("%30s", column);
                }
                System.out.println();
            }

            System.out.println();
        }
    }

    private static GaData executeDataQuery(Analytics analytics, String profileId) throws IOException {
        return analytics.data().ga().get("ga:" + profileId, // Table Id. ga: + profile id.
                "2015-11-10", // Start date.
                "2015-11-20", // End date.
                "ga:totalEvents,ga:uniqueEvents") // Metrics.
                .setDimensions("ga:eventAction,ga:eventLabel,ga:eventCategory")
                .setMaxResults(25)
                .execute();
    }

}
