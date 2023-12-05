package bean;

import com.google.gson.annotations.SerializedName;

/***
 * card的上方图片实体类
 *
 * @author xoliu
 * @create 23-11-21
 **/

public class CardPic {

    @SerializedName("code")
    private String code;
    @SerializedName("imgurl")
    private String imgurl;
    @SerializedName("width")
    private String width;
    @SerializedName("height")
    private String height;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
