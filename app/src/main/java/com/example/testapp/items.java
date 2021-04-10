package com.example.testapp;

import android.os.Parcel;
import android.os.Parcelable;

public class items implements Parcelable {
    private String name;
    private String category;
    private String url;
    private String rating;
    private String price;
    private Integer stock;
    private String brand;
    private String description;

    public items(String name, String category, String url, String rating, String price, Integer stock, String brand, String description) {
        this.name = name;
        this.category = category;
        this.url = url;
        this.rating = rating;
        this.price = price;
        this.stock = stock;
        this.brand = brand;
        this.description = description;
    }

    protected items(Parcel in) {
        name = in.readString();
        category = in.readString();
        url = in.readString();
        rating = in.readString();
        price = in.readString();
        if (in.readByte() == 0) {
            stock = null;
        } else {
            stock = in.readInt();
        }
        brand = in.readString();
        description = in.readString();
    }

    public static final Creator<items> CREATOR = new Creator<items>() {
        @Override
        public items createFromParcel(Parcel in) {
            return new items(in);
        }

        @Override
        public items[] newArray(int size) {
            return new items[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "items{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", url='" + url + '\'' +
                ", rating='" + rating + '\'' +
                ", price='" + price + '\'' +
                ", stock=" + stock +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(url);
        dest.writeString(rating);
        dest.writeString(price);
        if (stock == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(stock);
        }
        dest.writeString(brand);
        dest.writeString(description);
    }
}
