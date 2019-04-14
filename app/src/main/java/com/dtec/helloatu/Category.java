package com.dtec.helloatu;

public class Category {

    private int ctgPic;
    private int ctgBgColor;
    private String ctgTitleBangla;
    private String ctgTitleEnglish;

    public Category() {
    }

    public Category(int ctgPic, int ctgBgColor, String ctgTitleBangla, String ctgTitleEnglish) {
        this.ctgPic = ctgPic;
        this.ctgBgColor = ctgBgColor;
        this.ctgTitleBangla = ctgTitleBangla;
        this.ctgTitleEnglish = ctgTitleEnglish;
    }

    public int getCtgPic() {
        return ctgPic;
    }

    public void setCtgPic(int ctgPic) {
        this.ctgPic = ctgPic;
    }

    public int getCtgBgColor() {
        return ctgBgColor;
    }

    public void setCtgBgColor(int ctgBgColor) {
        this.ctgBgColor = ctgBgColor;
    }

    public String getCtgTitleBangla() {
        return ctgTitleBangla;
    }

    public void setCtgTitleBangla(String ctgTitleBangla) {
        this.ctgTitleBangla = ctgTitleBangla;
    }

    public String getCtgTitleEnglish() {
        return ctgTitleEnglish;
    }

    public void setCtgTitleEnglish(String ctgTitleEnglish) {
        this.ctgTitleEnglish = ctgTitleEnglish;
    }
}
