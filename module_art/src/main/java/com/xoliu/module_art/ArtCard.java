package com.xoliu.module_art;


import db.bean.ArtContent;

/***
 * 艺术画廊卡片的实体对象(已实现序列化)
 *
 * @author xoliu
 * @create 23-12-9
 **/

public class ArtCard{
    private int artImgId;
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
        this.artContent.setArtContentImg(artImgId);
        this.artContent.setName(name);
        this.artContent.setCreator(creator);
        this.artContent.setYear(year);
        this.artContent.setMaterial(material);
        this.artContent.setSize(size);
        this.artContent.setContent(content);
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
