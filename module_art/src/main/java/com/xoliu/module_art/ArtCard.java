package com.xoliu.module_art;

public class ArtCard {
    private int artImgId;
    private String artAuthor;
    private String artName;


    private String artContent;

    public ArtCard(int artImgId, String artAuthor, String artName, String artContent) {
        this.artImgId = artImgId;
        this.artAuthor = artAuthor;
        this.artName = artName;
        this.artContent = artContent;
    }
    public ArtCard(){

    }



    public String getArtContent() {
        return artContent;
    }

    public void setArtContent(String artContent) {
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
