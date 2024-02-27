package com.xoliu.module_poem.model.bean;

import com.google.gson.annotations.SerializedName;


/***
 * 换新的api
 * @return
 * @author xoliu
 * @create 24-1-28
 **/

/*
{
    "code": 0,
    "data": {
        "author": "李觏",
        "author_pinyin": "ligou",
        "catalog": "青春",
        "catalog_pinyin": "qingchun",
        "ctime": 1556292972,
        "id": 3833,
        "name": "秋晚悲怀",
        "sentence": "渐老多忧百事忙，天寒日短更心伤。",
        "src_url": "https://so.gushiwen.org/mingju/juv_ebb901ed539f.aspx",
        "theme": "人生",
        "theme_pinyin": "rensheng"
    },
    "msg": null,
    "q": {
        "author": "all",
        "catalog": "all",
        "suffix": "json",
        "theme": "all"
    }
}
 **/

public class Poemt {

    @SerializedName("code")
    private Integer code;
    @SerializedName("data")
    private Data data;
    @SerializedName("msg")
    private Object msg;
    @SerializedName("q")
    private Q q;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Q getQ() {
        return q;
    }

    public void setQ(Q q) {
        this.q = q;
    }

    public static class Data {
        @SerializedName("author")
        private String author;
        @SerializedName("author_pinyin")
        private String authorPinyin;
        @SerializedName("catalog")
        private String catalog;
        @SerializedName("catalog_pinyin")
        private String catalogPinyin;
        @SerializedName("ctime")
        private Integer ctime;
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("sentence")
        private String sentence;
        @SerializedName("src_url")
        private String srcUrl;
        @SerializedName("theme")
        private String theme;
        @SerializedName("theme_pinyin")
        private String themePinyin;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthorPinyin() {
            return authorPinyin;
        }

        public void setAuthorPinyin(String authorPinyin) {
            this.authorPinyin = authorPinyin;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public String getCatalogPinyin() {
            return catalogPinyin;
        }

        public void setCatalogPinyin(String catalogPinyin) {
            this.catalogPinyin = catalogPinyin;
        }

        public Integer getCtime() {
            return ctime;
        }

        public void setCtime(Integer ctime) {
            this.ctime = ctime;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSentence() {
            return sentence;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }

        public String getSrcUrl() {
            return srcUrl;
        }

        public void setSrcUrl(String srcUrl) {
            this.srcUrl = srcUrl;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getThemePinyin() {
            return themePinyin;
        }

        public void setThemePinyin(String themePinyin) {
            this.themePinyin = themePinyin;
        }
    }

    public static class Q {
        @SerializedName("author")
        private String author;
        @SerializedName("catalog")
        private String catalog;
        @SerializedName("suffix")
        private String suffix;
        @SerializedName("theme")
        private String theme;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }
    }
}
