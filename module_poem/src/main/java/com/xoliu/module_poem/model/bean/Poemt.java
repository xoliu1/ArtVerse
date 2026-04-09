package com.xoliu.module_poem.model.bean;

import com.google.gson.annotations.SerializedName;

/***
 * 今日诗词 API Bean (v1简版)
 * API: https://v1.jinrishici.com/all.json
 *
 * 返回格式（扁平结构，非常简单）:
 * {
 *   "content": "莫惊鸥鹭，四桥尽是，老子经行处。",
 *   "origin": "青玉案·送伯固归吴中",
 *   "author": "苏轼",
 *   "category": "古诗文-动物-写鸟"
 * }
 *
 * 为了兼容旧代码（fragment_viewpager_item 中调用 poem.getData().getSentence() 等），
 * 保留了 getData() 返回 this 自身包装的 Data 对象。
 *
 * @author xoliu
 * @create 24-1-28
 **/

public class Poemt {

    @SerializedName("content")
    private String content;

    @SerializedName("origin")
    private String origin;

    @SerializedName("author")
    private String author;

    @SerializedName("category")
    private String category;

    // ===== 兼容旧代码：getData() 返回一个内部包装对象 =====
    private transient Data dataWrapper;

    public Data getData() {
        if (dataWrapper == null) {
            dataWrapper = new Data(this);
        }
        return dataWrapper;
    }

    // ===== 标准 getter/setter =====

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

    /***
     * 内部兼容包装类，让旧代码 poem.getData().getSentence() 等调用不报错
     */
    public static class Data {
        private final Poemt poemt;

        public Data(Poemt poemt) {
            this.poemt = poemt;
        }

        /** 获取诗句正文 */
        public String getSentence() {
            return poemt.content;
        }

        /** 获取作者 */
        public String getAuthor() {
            return poemt.author;
        }

        /** 获取诗名（origin 字段） */
        public String getName() {
            return poemt.origin;
        }

        /** 旧 API 有 srcUrl，新 API 没有，返回空字符串 */
        public String getSrcUrl() {
            return "";
        }
    }
}
