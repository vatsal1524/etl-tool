package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {

        // Turning off the logs for MongoDb Driver
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(java.util.logging.Level.OFF);

        // API key to access https://newsapi.org
        String apiKey = "API_KEY_HERE";

        // List of keywords to retrieve news articles
        String[] keywords = new String[]{"Canada", "University", "Dalhousie", "Halifax", "Canada Education", "Moncton",
                "hockey", "Fredericton", "celebration"};

        URL url = null;

        for (String keyword : keywords) {
            try {
                url = new URL("https://newsapi.org/v2/everything?q=" + keyword + "&apiKey=" + apiKey);
//                url = new URL("http://localhost:8080/get/" + keyword);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            List<ApiResponse> apiResponseList = Extractor.extract(url);

            Processor.process(keyword, apiResponseList);

            // Establishing a connection to a MongoDB cluster using the MongoDB Java driver
            ConnectionString connectionString = new ConnectionString("MONGO_DB_CONNECTION_STRING");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();

            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("myMongoNews");

            Transformer.transformAndUploadRecords(keyword, database);
        }
    }
}
