package com.example.myapplication.ui.sinhvien.hocbong;

public class ItemHocBong {
    private String imgHB;
    private String dateHB;
    private String desHB;

    public ItemHocBong(String imgHB, String dateHB, String desHB) {
        this.imgHB = imgHB;
        this.dateHB = dateHB;
        this.desHB = desHB;
    }

    public String getImgHB() { return imgHB; }
    public void setImgHB(String imgHB) { this.imgHB = imgHB; }
    public String getDateHB() { return dateHB; }
    public void setDateHB(String dateHB) { this.dateHB = dateHB; }
    public String getDesHB() { return desHB; }
    public void setDesHB(String desHB) { this.desHB = desHB; }
}