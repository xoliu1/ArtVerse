package db.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ArtContent implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public
    int theId;
    int artContentImg;

    public int getArtContentImg() {
        return artContentImg;
    }

    public void setArtContentImg(int artContentImg) {
        this.artContentImg = artContentImg;
    }

    String name;
    String creator;
    String year;
    String material;
    String size;
    String content;

    public ArtContent(){

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
        dest.writeInt(this.artContentImg);
        dest.writeString(this.name);
        dest.writeString(this.creator);
        dest.writeString(this.year);
        dest.writeString(this.material);
        dest.writeString(this.size);
        dest.writeString(this.content);
    }

    public void readFromParcel(Parcel source) {
        this.artContentImg = source.readInt();
        this.name = source.readString();
        this.creator = source.readString();
        this.year = source.readString();
        this.material = source.readString();
        this.size = source.readString();
        this.content = source.readString();
    }

    protected ArtContent(Parcel in) {
        this.artContentImg = in.readInt();
        this.name = in.readString();
        this.creator = in.readString();
        this.year = in.readString();
        this.material = in.readString();
        this.size = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<ArtContent> CREATOR = new Parcelable.Creator<ArtContent>() {
        @Override
        public ArtContent createFromParcel(Parcel source) {
            return new ArtContent(source);
        }

        @Override
        public ArtContent[] newArray(int size) {
            return new ArtContent[size];
        }
    };
}
