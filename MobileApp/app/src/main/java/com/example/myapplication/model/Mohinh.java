package com.example.myapplication.model;

public class Mohinh {
    private String id;
    private String name;
    private String bvh;
    private String bvs;
    private String phamvi;

    public Mohinh(String id, String name, String bvs, String bvh, String phamvi) {
        this.id = id;
        this.name = name;
        this.bvs = bvs;
        this.bvh = bvh;
        this.phamvi = phamvi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBvh() {
        return bvh;
    }

    public void setBvh(String bvh) {
        this.bvh = bvh;
    }

    public String getBvs() {
        return bvs;
    }

    public void setBvs(String bvs) {
        this.bvs = bvs;
    }

    public String getPhamvi() {
        return phamvi;
    }

    public void setPhamvi(String phamvi) {
        this.phamvi = phamvi;
    }
}
