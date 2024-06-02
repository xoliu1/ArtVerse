package global;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LegalResult {
    @Override
    public String toString() {
        return "LegalResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }

    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private Result result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        @SerializedName("con")
        private String con;
        @SerializedName("con_type")
        private Integer conType;
        @SerializedName("list")
        private java.util.List<Result.list> list;

        @Override
        public String toString() {
            return "Result{" +
                    "con='" + con + '\'' +
                    ", conType=" + conType +
                    ", list=" + list +
                    '}';
        }

        public String getCon() {
            return con;
        }

        public void setCon(String con) {
            this.con = con;
        }

        public Integer getConType() {
            return conType;
        }

        public void setConType(Integer conType) {
            this.conType = conType;
        }

        public List<Result.list> getList() {
            return list;
        }

        public void setList(List<Result.list> list) {
            this.list = list;
        }

        public static class list {
            @SerializedName("msg")
            private String msg;
            @SerializedName("subtype")
            private Integer subtype;
            @SerializedName("words")
            private java.util.List<String> words;
            @SerializedName("deets")
            private java.util.List<?> deets;
            @SerializedName("trust")
            private Integer trust;

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public Integer getSubtype() {
                return subtype;
            }

            public void setSubtype(Integer subtype) {
                this.subtype = subtype;
            }


            public List<String> getWords() {
                return words;
            }

            public void setWords(List<String> words) {
                this.words = words;
            }

            @Override
            public String toString() {
                return "list{" +
                        "msg='" + msg + '\'' +
                        ", subtype=" + subtype +
                        ", words=" + words +
                        ", deets=" + deets +
                        ", trust=" + trust +
                        '}';
            }

            public List<?> getDeets() {
                return deets;
            }

            public void setDeets(List<?> deets) {
                this.deets = deets;
            }

            public Integer getTrust() {
                return trust;
            }

            public void setTrust(Integer trust) {
                this.trust = trust;
            }
        }
    }
}
