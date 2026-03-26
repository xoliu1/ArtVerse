package global;

import com.google.gson.annotations.SerializedName;

/*
 * ============== Answer.java 说明（DeepSeek 改法） ==============
 *
 * 这个类是用来解析文心一言返回的 JSON 的。
 *
 * 【文心一言的返回格式】：
 *   {
 *     "id": "xxx",
 *     "result": "AI的回复内容",    ← 直接从顶层 result 字段取
 *     "is_truncated": false,
 *     "usage": { ... }
 *   }
 *
 * 【DeepSeek 的返回格式】（OpenAI 兼容格式）：
 *   {
 *     "id": "xxx",
 *     "choices": [                  ← AI回复藏在 choices 数组里
 *       {
 *         "index": 0,
 *         "message": {
 *           "role": "assistant",
 *           "content": "AI的回复内容"  ← 要取这个
 *         },
 *         "finish_reason": "stop"
 *       }
 *     ],
 *     "usage": { ... }
 *   }
 *
 * 【结论】：
 *   如果改用 DeepSeek，这个 Answer 类就不适用了。
 *   有两种选择：
 *
 *   选择A（简单）：不用 Gson 解析，直接用 JSONObject 取值（推荐，已在 ChatAI.java 和 ComposePoem.java 的注释中写好）
 *     JSONObject json = new JSONObject(responseBodyString);
 *     String result = json.getJSONArray("choices")
 *                         .getJSONObject(0)
 *                         .getJSONObject("message")
 *                         .getString("content");
 *
 *   选择B（规范）：新建一个 DeepSeek 专用的响应类：
 *     public class DeepSeekAnswer {
 *         private List<Choice> choices;
 *         public String getResult() {
 *             return choices.get(0).message.content;
 *         }
 *         public static class Choice {
 *             private Message message;
 *             public static class Message {
 *                 private String role;
 *                 private String content;
 *             }
 *         }
 *     }
 *
 * ==================================================
 */

public class Answer {

    @SerializedName("id")
    private String id;
    @SerializedName("object")
    private String object;
    @SerializedName("created")
    private Integer created;
    @SerializedName("result")
    private String result;
    @SerializedName("is_truncated")
    private Boolean isTruncated;
    @SerializedName("need_clear_history")
    private Boolean needClearHistory;
    @SerializedName("finish_reason")
    private String finishReason;
    @SerializedName("usage")
    private Usage usage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getIsTruncated() {
        return isTruncated;
    }

    public void setIsTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    public Boolean getNeedClearHistory() {
        return needClearHistory;
    }

    public void setNeedClearHistory(Boolean needClearHistory) {
        this.needClearHistory = needClearHistory;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public static class Usage {
        @SerializedName("prompt_tokens")
        private Integer promptTokens;
        @SerializedName("completion_tokens")
        private Integer completionTokens;
        @SerializedName("total_tokens")
        private Integer totalTokens;

        public Integer getPromptTokens() {
            return promptTokens;
        }

        public void setPromptTokens(Integer promptTokens) {
            this.promptTokens = promptTokens;
        }

        public Integer getCompletionTokens() {
            return completionTokens;
        }

        public void setCompletionTokens(Integer completionTokens) {
            this.completionTokens = completionTokens;
        }

        public Integer getTotalTokens() {
            return totalTokens;
        }

        public void setTotalTokens(Integer totalTokens) {
            this.totalTokens = totalTokens;
        }
    }
}
