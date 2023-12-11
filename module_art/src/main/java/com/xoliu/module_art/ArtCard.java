package com.xoliu.module_art;


/***
 * 艺术画廊卡片的实体对象(已实现序列化)
 *
 * @author xoliu
 * @create 23-12-9
 **/

public class ArtCard{
    private int artImgId = R.drawable.pic1;
    private String artAuthor;
    private String artName;


    private ArtContent artContent;




    public ArtCard(int artImgId, String artAuthor, String artName, ArtContent artContent) {
        this.artImgId = artImgId;
        this.artAuthor = artAuthor;
        this.artName = artName;
        this.artContent = artContent;
    }


    //自定义构造，方便导入数据
    public ArtCard(int artImgId, String artAuthor, String artName, String name, String creator, String year, String material, String size, String content) {
        this.artImgId = artImgId;
        this.artAuthor = artAuthor;
        this.artName = artName;
        this.artContent = new ArtContent();
        this.artContent.artContentImg = artImgId;
        this.artContent.name = name;
        this.artContent.creator = creator;
        this.artContent.year = year;
        this.artContent.material = material;
        this.artContent.size = size;
        this.artContent.content = content;
    }





    public ArtCard(){

    }



    public ArtContent getArtContent() {
        return artContent;
    }

    public void setArtContent(ArtContent artContent) {
        this.artContent = artContent;
    }

    public int getArtImgId() {
        return artImgId;
    }

    public void setArtImgId(int artImgId) {
        this.artImgId = artImgId;
    }

    public String getArtAuthor() {
        return artAuthor;
    }

    public void setArtAuthor(String artAuthor) {
        this.artAuthor = artAuthor;
    }

    public String getArtName() {
        return artName;
    }

    public void setArtName(String artName) {
        this.artName = artName;
    }








}
