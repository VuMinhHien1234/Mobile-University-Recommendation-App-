package com.example.myapplication.model;

import java.util.List;

public class KiHoc {
    private String ten;
    private List<MonHoc> monHocList;


    public KiHoc(String ten, List<MonHoc> monHocList) {
        this.ten = ten;
        this.monHocList = monHocList;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public List<MonHoc> getMonHocList() {
        return monHocList;
    }

    public void setMonHocList(List<MonHoc> monHocList) {
        this.monHocList = monHocList;
    }
}
