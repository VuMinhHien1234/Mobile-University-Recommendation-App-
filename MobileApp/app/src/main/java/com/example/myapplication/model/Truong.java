package com.example.myapplication.model;

import java.util.List;

public class Truong {
    private String code;
    private String ten;
    private String moTa;
    private String hinhAnhResId;
    private String hocPhi;
    private String group;
    private float rating;
    private List<String> dsAnh;
    private List<String> uuDiem;
    private String diachi;
    private String highlight;
    private String environment;
    private String teaching_method;
    private String network;


    public Truong(String code, String ten, String hinhAnhResId, String moTa, String hocPhi, String group, float rating, List<String> uuDiem, List<String> dsAnh, String diachi, String environment, String highlight, String network, String teaching_method) {
        this.code = code;
        this.ten = ten;
        this.hinhAnhResId = hinhAnhResId;
        this.moTa = moTa;
        this.hocPhi = hocPhi;
        this.group = group;
        this.rating = rating;
        this.uuDiem = uuDiem;
        this.dsAnh = dsAnh;
        this.diachi = diachi;
        this.environment = environment;
        this.highlight = highlight;
        this.network = network;
        this.teaching_method = teaching_method;
    }

    public String getCode() {
        return code;
    }

    public String getTen() {
        return ten;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getHinhAnhResId() {
        return hinhAnhResId;
    }

    public String getHocPhi() {
        return hocPhi;
    }

    public float getRating() {
        return rating;
    }

    public String getGroup() {
        return group;
    }

    public List<String> getDsAnh() {
        return dsAnh;
    }

    public List<String> getUuDiem() {
        return uuDiem;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getHighlight() {
        return highlight;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getNetwork() {
        return network;
    }

    public String getTeaching_method() {
        return teaching_method;
    }
}
