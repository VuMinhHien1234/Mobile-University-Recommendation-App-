package com.example.myapplication.ui.gioithieu.cocautochuc;

public class CoCauToChuc {
    String logoUrl;
    String titleCCTC, phoneCCTC, emailCCTC, addressCCTC;

    public CoCauToChuc(String logoUrl, String titleCCTC, String phoneCCTC, String emailCCTC, String addressCCTC) {
        this.logoUrl = logoUrl;
        this.titleCCTC = titleCCTC;
        this.phoneCCTC = phoneCCTC;
        this.emailCCTC = emailCCTC;
        this.addressCCTC = addressCCTC;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setTitleCCTC(String titleCCTC) {
        this.titleCCTC = titleCCTC;
    }

    public String getTitleCCTC() {
        return titleCCTC;
    }

    public void setPhoneCCTC(String phoneCCTC) {
        this.phoneCCTC = phoneCCTC;
    }

    public String getPhoneCCTC() {
        return phoneCCTC;
    }

    public void setEmailCCTC(String emailCCTC) {
        this.emailCCTC = emailCCTC;
    }

    public String getEmailCCTC() {
        return emailCCTC;
    }

    public void setAddressCCTC(String addressCCTC) {
        this.addressCCTC = addressCCTC;
    }

    public String getAddressCCTC() {
        return addressCCTC;
    }
}