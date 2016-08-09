package ru.yandex.yamblz.loaders.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vorona on 09.08.16.
 */

public class Skate implements Parcelable {
    private String name;
    private String full_name;
    private String description;
    private int imgSkateResId; // Url must be here
    private int imgCompResId;

    public Skate() {}

    public Skate(String name, String full_name, String description, int idSkate, int idComp) {
        this.name = name;
        this.full_name = full_name;
        this.description = description;
        this.imgSkateResId = idSkate;
        this.imgCompResId = idComp;
    }

    protected Skate(Parcel in) {
        name = in.readString();
        full_name = in.readString();
        description = in.readString();
        imgSkateResId = in.readInt();
        imgCompResId = in.readInt();
    }

    public static final Creator<Skate> CREATOR = new Creator<Skate>() {
        @Override
        public Skate createFromParcel(Parcel in) {
            return new Skate(in);
        }

        @Override
        public Skate[] newArray(int size) {
            return new Skate[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getImgCompResId() {
        return imgCompResId;
    }

    public int getImgSkateResId() {
        return imgSkateResId;
    }

    public String getDescription() {
        return description;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setImgCompResId(int imgCompResId) {
        this.imgCompResId = imgCompResId;
    }

    public void setImgSkateResId(int imgSkateResId) {
        this.imgSkateResId = imgSkateResId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(full_name);
        dest.writeString(description);
        dest.writeInt(imgSkateResId);
        dest.writeInt(imgCompResId);
    }
}
