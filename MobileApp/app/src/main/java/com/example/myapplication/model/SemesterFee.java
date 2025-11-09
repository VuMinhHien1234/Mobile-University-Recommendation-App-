package com.example.myapplication.model;

public class SemesterFee {
    private final String ki;       // "Kì 1", "Kì 2", ...
    private final long tongTinChiDaDK; // ví dụ: 845000 (đồng hoặc điểm TC tuỳ bạn)
    private final int tongSoTinChi;    // 11, 16...
    private final long tongHocPhi;     // 10385000...

    public SemesterFee(String ki, long tongTinChiDaDK, int tongSoTinChi, long tongHocPhi) {
        this.ki = ki;
        this.tongTinChiDaDK = tongTinChiDaDK;
        this.tongSoTinChi = tongSoTinChi;
        this.tongHocPhi = tongHocPhi;
    }
    public String getKi() { return ki; }
    public long getTongTinChiDaDK() { return tongTinChiDaDK; }
    public int getTongSoTinChi() { return tongSoTinChi; }
    public long getTongHocPhi() { return tongHocPhi; }
}

