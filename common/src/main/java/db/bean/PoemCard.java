package db.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import bean.CardPic;
import bean.Poem;

/***
 * 用来存数据库的bean类
 * 内含诗歌card的view构建参数，由CardPic 和 Poem；相关参数构成
 * @return
 * @author xoliu
 * @create 23-12-6
 **/

@Entity
public class PoemCard {
    @PrimaryKey(autoGenerate = true)
    int id;
    String imgUrl;
    String poemContext;
    String poemAuthor;

    public PoemCard(){
    }

    @Ignore
    public PoemCard(CardPic cardPic, Poem poem){
        this.imgUrl = cardPic.getImgurl();
        this.poemContext = poem.getHitokoto();
        this.poemContext = poem.getFrom();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPoemContext() {
        return poemContext;
    }

    public void setPoemContext(String poemContext) {
        this.poemContext = poemContext;
    }

    public String getPoemAuthor() {
        return poemAuthor;
    }

    public void setPoemAuthor(String poemAuthor) {
        this.poemAuthor = poemAuthor;
    }
}
