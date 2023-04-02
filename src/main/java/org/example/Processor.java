package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Processor {

    /**
     * This method is processes the list of ApiResponse object by storing them into a defined file structure.
     *
     * @param keyword The keyword for the news article.
     * @param apiResponseList The list of ApiResponse object.
     */
    public static void process(String keyword, List<ApiResponse> apiResponseList) {
        try {
            File directory = new File("news");
            directory.mkdir();
            File subDirectory = new File("news" + File.separator + keyword);
            subDirectory.mkdir();

            int count = 0;
            int i = 0;

            for (ApiResponse apiResponse : apiResponseList) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("news" + File.separator + keyword + File.separator + "file-" + (i + 1) + ".txt", true));
                writer.write(apiResponse.getTitle() + "\n");
                writer.write(apiResponse.getContent() + "\n");
                count++;

                // Changing the file when 5 records have been inserted
                if (count % 5 == 0) i++;
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
