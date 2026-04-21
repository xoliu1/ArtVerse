package com.xoliu.module_art;


import db.bean.ArtContent;
import db.bean.GalleryBean;

/***
 * 艺术画廊卡片的实体对象(已实现序列化)
 *
 * @author xoliu
 * @create 23-12-9
 **/

public class ArtCard{

    // 卡片类型常量
    public static final int TYPE_MASTERPIECE = 0;   // 名作
    public static final int TYPE_PERSONAL = 2;       // 个人作品

    private int cardType = TYPE_MASTERPIECE; // 默认是名作
    private int artImgId;
    private String artAuthor;
    private String artName;
    private String imageUrl;  // 网络图片URL（个人作品用）

    private ArtContent artContent;
    private GalleryBean galleryBean; // 个人作品的原始数据


    public ArtCard(int artImgId, String artAuthor, String artName, ArtContent artContent) {
        this.artImgId = artImgId;
        this.artAuthor = artAuthor;
        this.artName = artName;
        this.artContent = artContent;
        this.cardType = TYPE_MASTERPIECE;
    }


    //自定义构造，方便导入数据
    public ArtCard(int artImgId, String artAuthor, String artName, String name, String creator, String year, String material, String size, String content) {
        this.artImgId = artImgId;
        this.artAuthor = artAuthor;
        this.artName = artName;
        this.cardType = TYPE_MASTERPIECE;
        this.artContent = new ArtContent();
        this.artContent.setArtContentImg(artImgId);
        this.artContent.setName(name);
        this.artContent.setCreator(creator);
        this.artContent.setYear(year);
        this.artContent.setMaterial(material);
        this.artContent.setSize(size);
        this.artContent.setContent(content);
    }

    /**
     * 从 GalleryBean 构造个人作品卡片
     */
    public static ArtCard fromGalleryBean(GalleryBean bean) {
        ArtCard card = new ArtCard();
        card.cardType = TYPE_PERSONAL;
        card.artName = bean.getTitle();
        card.artAuthor = bean.getCreator() != null && !bean.getCreator().isEmpty()
                ? bean.getCreator() : bean.getUsername();
        card.imageUrl = bean.getImageUrl();
        card.galleryBean = bean;

        // 同时构建 ArtContent 以便详情页复用
        ArtContent ac = new ArtContent();
        ac.setName(bean.getTitle());
        ac.setCreator(bean.getCreator() != null ? bean.getCreator() : "");
        ac.setYear(bean.getYear() != null ? bean.getYear() : "");
        ac.setMaterial(bean.getMaterial() != null ? bean.getMaterial() : "");
        ac.setSize(bean.getSize() != null ? bean.getSize() : "");
        ac.setContent(bean.getDescription() != null ? bean.getDescription() : "");
        card.artContent = ac;

        return card;
    }



    public ArtCard(){

    }


    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public GalleryBean getGalleryBean() {
        return galleryBean;
    }

    public void setGalleryBean(GalleryBean galleryBean) {
        this.galleryBean = galleryBean;
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
