package global;

import com.google.gson.annotations.SerializedName;

public class XPoemA {

    @SerializedName("content")
    private String content;
    @SerializedName("origin")
    private String origin;
    @SerializedName("author")
    private String author;
    @SerializedName("category")
    private String category;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
