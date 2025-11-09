package com.example.myapplication.model;

public class ToHop {
    private String id;
    private String name;
    private String tohop;

    public ToHop(String id, String name, String tohop) {
        this.id = id;
        this.name = name;
        this.tohop = tohop;
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

    public String getTohop() {
        return tohop;
    }

    public void setTohop(String tohop) {
        this.tohop = tohop;
    }
}
