package global;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class XPoemB {

    @SerializedName("code")
    private Integer code;
    @SerializedName("data")
    private List<Data> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Data> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "XPoemB{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("content")
        private String content;
        @SerializedName("origin")
        private String origin;
        @SerializedName("author")
        private String author;
        @SerializedName("category")
        private String category;

        @Override
        public String toString() {
            return "Data{" +
                    "content='" + content + '\'' +
                    ", origin='" + origin + '\'' +
                    ", author='" + author + '\'' +
                    ", category='" + category + '\'' +
                    '}';
        }

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
}
