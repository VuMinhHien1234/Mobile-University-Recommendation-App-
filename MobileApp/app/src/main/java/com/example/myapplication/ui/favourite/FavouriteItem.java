package com.example.myapplication.ui.favourite;

public class FavouriteItem {
    private final String NameSchool;
    private final String Location;
    private final String imageSchool;

    public FavouriteItem(String NameSchool, String Location, String imageSchool) {
        this.NameSchool = NameSchool;
        this.Location = Location;
        this.imageSchool = imageSchool;
    }

    public String getTitle() {
        return NameSchool;
    }

    public String getDescription() {
        return Location;
    }

    public String getImageUrl() {
        return imageSchool;
    }
}
