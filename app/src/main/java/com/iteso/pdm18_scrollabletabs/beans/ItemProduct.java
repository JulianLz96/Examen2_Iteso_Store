package com.iteso.pdm18_scrollabletabs.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Oscar Vargas
 * @since 26/02/18.
 *
 * @version 1.0.1 Add Parcelable Interfaz and code parameter
 */

public class ItemProduct implements Parcelable{
    private int code;
    private String title;
    private String store;
    private String location;
    private String phone;
    private String description;
    private Integer image;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ItemProduct{" +
                "code=" + code +
                ", title='" + title + '\'' +
                ", store='" + store + '\'' +
                ", location='" + location + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                '}';
    }

    public ItemProduct(int code, String title, String store, String location, String phone, String description, Integer image) {
        this.code = code;
        this.title = title;
        this.store = store;
        this.location = location;
        this.phone = phone;
        this.description = description;
        this.image = image;
    }

    public ItemProduct(String title, String store) {
        this.title = title;
        this.store = store;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.title);
        dest.writeString(this.store);
        dest.writeString(this.location);
        dest.writeString(this.phone);
        dest.writeString(this.description);
        dest.writeValue(this.image);
    }

    ItemProduct(Parcel in) {
        this.code = in.readInt();
        this.title = in.readString();
        this.store = in.readString();
        this.location = in.readString();
        this.phone = in.readString();
        this.description = in.readString();
        this.image = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<ItemProduct> CREATOR = new Creator<ItemProduct>() {
        @Override
        public ItemProduct createFromParcel(Parcel source) {
            return new ItemProduct(source);
        }

        @Override
        public ItemProduct[] newArray(int size) {
            return new ItemProduct[size];
        }
    };
}
