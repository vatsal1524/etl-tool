package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extractor {

    /**
     * This method is used to extract a list of ApiResponse object from the JSON response received by sending a GET request to the provided URL.
     *
     * @param url The URL where GET request is to be sent.
     * @return returns a list of ApiResponse object.
     * @throws RuntimeException If there is a runtime error.
     */
    public static List<ApiResponse> extract(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // creating an HTTP connection
            connection.setRequestMethod("GET"); // sending GET request

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer responseContent = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();

            // Matching a Regex pattern to extract the titles and contents from the JSON String
            Pattern pattern = Pattern.compile("\"title\":\"((?:[^\"\\\\]|\\\\.)*)\"(.*?(?=\"content\":\"([\\s\\S]+?(?=\"}(,\\{|])))))");
            Matcher matcher = pattern.matcher(responseContent);

            List<ApiResponse> apiResponseList = new ArrayList<>();
            while (matcher.find()) {
                apiResponseList.add(new ApiResponse(matcher.group(1), matcher.group(3)));
            }

            return apiResponseList;
        } catch(RuntimeException | IOException e) {
            return null;
        }
    }
}
