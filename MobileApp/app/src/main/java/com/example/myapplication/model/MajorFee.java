package com.example.myapplication.model;
public class MajorFee {
    private final String tenNganh;
    private final String maNganh;
    private final long hocPhi; // dùng long để format tiền tệ

    public MajorFee(String tenNganh, String maNganh, long hocPhi) {
        this.tenNganh = tenNganh;
        this.maNganh = maNganh;
        this.hocPhi = hocPhi;
    }
    public String getTenNganh() { return tenNganh; }
    public String getMaNganh() { return maNganh; }
    public long getHocPhi() { return hocPhi; }
}
