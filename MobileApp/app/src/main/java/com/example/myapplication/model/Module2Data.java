package com.example.myapplication.model;

public class Module2Data {
    private String academic_fit_score;
    private String benmark;
    private String tohop;
    private String final_score;
    private String major_name;
    private String  major_simlarity_score;
    private String method;
    private String score_match;
    private String suitable_rate;
    private String universityId;
    private String university_score;
    private String user_score;

    public Module2Data(String academic_fit_score, String benmark, String tohop, String major_name, String final_score, String method, String major_simlarity_score, String suitable_rate, String universityId, String user_score, String university_score, String score_match) {
        this.academic_fit_score = academic_fit_score;
        this.benmark = benmark;
        this.tohop = tohop;
        this.major_name = major_name;
        this.final_score = final_score;
        this.method = method;
        this.major_simlarity_score = major_simlarity_score;
        this.suitable_rate = suitable_rate;
        this.universityId = universityId;
        this.user_score = user_score;
        this.university_score = university_score;
        this.score_match = score_match;
    }

    public String getAcademic_fit_score() {
        return academic_fit_score;
    }

    public void setAcademic_fit_score(String academic_fit_score) {
        this.academic_fit_score = academic_fit_score;
    }

    public String getBenmark() {
        return benmark;
    }

    public void setBenmark(String benmark) {
        this.benmark = benmark;
    }

    public String getFinal_score() {
        return final_score;
    }

    public void setFinal_score(String final_score) {
        this.final_score = final_score;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public String getTohop() {
        return tohop;
    }

    public void setTohop(String tohop) {
        this.tohop = tohop;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getUniversity_score() {
        return university_score;
    }

    public void setUniversity_score(String university_score) {
        this.university_score = university_score;
    }

    public String getMajor_simlarity_score() {
        return major_simlarity_score;
    }

    public void setMajor_simlarity_score(String major_simlarity_score) {
        this.major_simlarity_score = major_simlarity_score;
    }

    public String getScore_match() {
        return score_match;
    }

    public void setScore_match(String score_match) {
        this.score_match = score_match;
    }

    public String getSuitable_rate() {
        return suitable_rate;
    }

    public void setSuitable_rate(String suitable_rate) {
        this.suitable_rate = suitable_rate;
    }

    public String getUser_score() {
        return user_score;
    }

    public void setUser_score(String user_score) {
        this.user_score = user_score;
    }
}
