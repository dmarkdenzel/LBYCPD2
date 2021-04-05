package com.example.testapp;

public class items {
    private String name;
    private String category;
    private String url;
    private String rating;

    public items(String name, String category, String url, String rating) {
        this.name = name;
        this.category = category;
        this.url = url;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    public String getRating() {
        return rating;
    }

    public void setNamee(String namee) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "items{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", url='" + url + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
