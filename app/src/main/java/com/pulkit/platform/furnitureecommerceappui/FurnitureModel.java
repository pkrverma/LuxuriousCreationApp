package com.pulkit.platform.furnitureecommerceappui;

public class FurnitureModel {
    private String name;
    private String price;
    private String shopName;
    private String rating;
    private String description;
    private int imageResId;

    // Constructor
    public FurnitureModel(String name, String price, String shopName, String rating, String description, int imageResId) {
        this.name = name;
        this.price = price;
        this.shopName = shopName;
        this.rating = rating;
        this.description = description;
        this.imageResId = imageResId;
    }

//    // Getter methods
//    public String getName() {
//        return name;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public String getShopName() {
//        return shopName;
//    }
//
//    public String getRating() {
//        return rating;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public int getImageResId() {
//        return imageResId;
//    }
}
