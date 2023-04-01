package org.example;
public class ApiResponse {
    private String title;
    private String content;

    /**
     * This constructor is used to create an ApiResponse object.
     *
     * @param title The title of the article.
     * @param content The content of the article.
     */
    public ApiResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * This method is used to get the title of the article.
     *
     * @return returns the title of the article.
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method is used to set the title of the article.
     *
     * @param title The title of the article.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method is used to get the content of the article.
     *
     * @return returns the content of the article.
     */
    public String getContent() {
        return content;
    }

    /**
     * This method is used to set the content of the article.
     *
     * @param content The title of the article.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * This method is used to print the ApiResponse object.
     *
     * @return returns the ApiResponse object as a String.
     */
    @Override
    public String toString() {
        return "ApiResponse{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
