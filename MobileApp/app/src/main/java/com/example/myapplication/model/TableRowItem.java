package com.example.myapplication.model;

public class TableRowItem {
    private final int stt;
    private final String col1; // Nội dung cột giữa
    private final String col2; // Tỷ lệ (hoặc %)

    public TableRowItem(int stt, String col1, String col2) {
        this.stt = stt;
        this.col1 = col1;
        this.col2 = col2;
    }

    public int getStt() {
        return stt;
    }

    public String getCol1() {
        return col1;
    }

    public String getCol2() {
        return col2;
    }
}
