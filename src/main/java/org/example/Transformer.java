package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Transformer {

    /**
     * This method is used to transform the raw data by removing the unwanted elements.
     *
     * @param rawData The line read from the file.
     * @return returns a String object of the transformed data.
     */
    private static String transform(String rawData) {
        return rawData
                .replaceAll("\\s*…\\s*\\[\\+\\d+ chars\\]", "")
                .replaceAll("\u00A0", " ")
                .replaceAll("&amp;", "&")
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("\\\\\"", "\"")
                .replaceAll("\\\\r", "")
                .replaceAll("\\\\n", "")
                .replaceAll("<ul>", ": ")
                .replaceAll("</li>", ", ")
                .replaceAll("<li>", "");
    }

    /**
     * This method is used to transform and upload the raw data read from the file to the provided database.
     *
     * @param keyword The keyword for the news article.
     * @param database The database where the transformed data will be stored.
     */
    public static void transformAndUploadRecords(String keyword, MongoDatabase database) {
        try {
            File[] files = new File("news" + File.separator + keyword).listFiles();

            // Move to a specific collection based on the keyword
            MongoCollection<Document> collection = database.getCollection(keyword);

            for (File file : files) {
                BufferedReader br = new BufferedReader(new FileReader(file));

                List<Document> documents = new ArrayList<>();

                String title;
                String content;

                // Iterate through the file and transform the title and content
                while ((title = br.readLine()) != null && (content = br.readLine()) != null) {
                    documents.add(new Document()
                            .append("title", transform(title))
                            .append("content", transform(content)));
                }

                // Bulk insert operation
                collection.insertMany(documents);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
