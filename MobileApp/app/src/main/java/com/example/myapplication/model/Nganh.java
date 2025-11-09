package com.example.myapplication.model;


import java.util.List;

public class Nganh {
    private String name;
    private String id;
    private String duration;
    private String campus;

    private String intakeSemester;

    public Nganh(String id,String name, String duration, String campus, String intakeSemester) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.campus = campus;
        this.intakeSemester = intakeSemester;

    }
    public Nganh(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getIntakeSemester() {
        return intakeSemester;
    }

    public void setIntakeSemester(String intakeSemester) {
        this.intakeSemester = intakeSemester;
    }
}