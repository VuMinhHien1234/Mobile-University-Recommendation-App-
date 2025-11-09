package com.example.myapplication.ui.sinhvien.hoatdongsinhvien;

public class ItemHoatDongSinhVien {
    private String imageUrl;
    private int iconCalendar;
    private String date;
    private String des;

    public ItemHoatDongSinhVien(String imageUrl, int iconCalendar, String date, String des) {
        this.imageUrl = imageUrl;
        this.iconCalendar = iconCalendar;
        this.date = date;
        this.des = des;
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public int getIconCalendar() { return iconCalendar; }
    public void setIconCalendar(int iconCalendar) { this.iconCalendar = iconCalendar; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDes() { return des; }
    public void setDes(String des) { this.des = des; }
}