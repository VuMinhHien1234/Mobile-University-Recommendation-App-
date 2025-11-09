package com.example.myapplication.ui.sinhvien.clb;

public class ItemCLB {
    private String logoImage;
    private String clubName;
    private String leaderName;
    private String facebookLink;

    public ItemCLB(String logoImage, String clubName, String leaderName, String facebookLink) {
        this.logoImage = logoImage;
        this.clubName = clubName;
        this.leaderName = leaderName;
        this.facebookLink = facebookLink;
    }

    public String getLogoImage() { return logoImage; }
    public void setLogoImage(String logoImage) { this.logoImage = logoImage; }
    public String getClubName() { return clubName; }
    public void setClubName(String clubName) { this.clubName = clubName; }
    public String getLeaderName() { return leaderName; }
    public void setLeaderName(String leaderName) { this.leaderName = leaderName; }
    public String getFacebookLink() { return facebookLink; }
    public void setFacebookLink(String facebookLink) { this.facebookLink = facebookLink; }
}