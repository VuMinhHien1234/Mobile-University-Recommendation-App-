package com.example.myapplication.model;
public class Partner {
    public final String name;
    public final String imageUrl; // <-- Đã đổi từ int sang String

    public Partner(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
