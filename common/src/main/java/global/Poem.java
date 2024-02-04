package global;

import com.google.gson.annotations.SerializedName;


/***
 * 诗句的Json接受类
 *
 * @return
 * @author xoliu
 * @create 23-12-4
 **/

public class Poem {
    /*
    返回参数名称	描述
    id	一言标识
    hitokoto	一言正文。编码方式 unicode。使用 utf-8。
    type	类型。请参考第三节参数的表格
    from	一言的出处
    from_who	一言的作者
    creator	添加者
    creator_uid	添加者用户标识
    reviewer	审核员标识
    uuid	一言唯一标识；可以链接到 https://hitokoto.cn?uuid=[uuid] 查看这个一言的完整信息
    commit_from	提交方式
    created_at	添加时间
    length	句子长度
     **/

    @SerializedName("id")
    private Integer id;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("hitokoto")
    private String hitokoto;
    @SerializedName("type")
    private String type;
    @SerializedName("from")
    private String from;
    @SerializedName("from_who")
    private String fromWho = "佚名";
    @SerializedName("creator")
    private String creator;
    @SerializedName("creator_uid")
    private Integer creatorUid;
    @SerializedName("reviewer")
    private Integer reviewer;
    @SerializedName("commit_from")
    private String commitFrom;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("length")
    private Integer length;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromWho() {
        return fromWho;
    }

    public void setFromWho(String fromWho) {
        this.fromWho = fromWho;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(Integer creatorUid) {
        this.creatorUid = creatorUid;
    }

    public Integer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Integer reviewer) {
        this.reviewer = reviewer;
    }

    public String getCommitFrom() {
        return commitFrom;
    }

    public void setCommitFrom(String commitFrom) {
        this.commitFrom = commitFrom;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
