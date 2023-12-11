package com.xoliu.module_art;


import android.os.Parcel;
import android.os.Parcelable;

/***
 * 艺术画廊卡片的实体对象(已实现序列化)
 *
 * @author xoliu
 * @create 23-12-9
 **/

public class ArtCard implements Parcelable {
    private int artImgId = R.drawable.pic1;
    private String artAuthor;
    private String artName;


    private artContent artContent;




    public ArtCard(int artImgId, String artAuthor, String artName, artContent artContent) {
        this.artImgId = artImgId;
        this.artAuthor = artAuthor;
        this.artName = artName;
        this.artContent = artContent;
    }
    public ArtCard(){

    }



    public artContent getArtContent() {
        return artContent;
    }

    public void setArtContent(artContent artContent) {
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

    class artContent implements Parcelable {


        String name;
        String creator;
        String year;
        String material;
        String size;
        String content;

        public artContent(String name, String creator, String year, String material, String size, String content) {
            this.name = name;
            this.creator = creator;
            this.year = year;
            this.material = material;
            this.size = size;
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMaterial() {
            return material;
        }

        public void setMaterial(String material) {
            this.material = material;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.creator);
            dest.writeString(this.year);
            dest.writeString(this.material);
            dest.writeString(this.size);
            dest.writeString(this.content);
        }

        public void readFromParcel(Parcel source) {
            this.name = source.readString();
            this.creator = source.readString();
            this.year = source.readString();
            this.material = source.readString();
            this.size = source.readString();
            this.content = source.readString();
        }

        protected artContent(Parcel in) {
            this.name = in.readString();
            this.creator = in.readString();
            this.year = in.readString();
            this.material = in.readString();
            this.size = in.readString();
            this.content = in.readString();
        }

        public final Parcelable.Creator<artContent> CREATOR = new Parcelable.Creator<artContent>() {
            @Override
            public artContent createFromParcel(Parcel source) {
                return new artContent(source);
            }

            @Override
            public artContent[] newArray(int size) {
                return new artContent[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.artImgId);
        dest.writeString(this.artAuthor);
        dest.writeString(this.artName);
        dest.writeParcelable(this.artContent, flags);
    }

    public void readFromParcel(Parcel source) {
        this.artImgId = source.readInt();
        this.artAuthor = source.readString();
        this.artName = source.readString();
        this.artContent = source.readParcelable(ArtCard.artContent.class.getClassLoader());
    }

    protected ArtCard(Parcel in) {
        this.artImgId = in.readInt();
        this.artAuthor = in.readString();
        this.artName = in.readString();
        this.artContent = in.readParcelable(ArtCard.artContent.class.getClassLoader());
    }

    public static final Parcelable.Creator<ArtCard> CREATOR = new Parcelable.Creator<ArtCard>() {
        @Override
        public ArtCard createFromParcel(Parcel source) {
            return new ArtCard(source);
        }

        @Override
        public ArtCard[] newArray(int size) {
            return new ArtCard[size];
        }
    };
}
