package com.dtec.helloatu.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CRIME".
 */
public class Crime {

    private Long id;
    /** Not-null value. */
    private String crimeInfo;
    /** Not-null value. */
    private String occurrancePlace;
    /** Not-null value. */
    private String informerName;
    /** Not-null value. */
    private String informerPhone;
    /** Not-null value. */
    private String informerAddress;
    /** Not-null value. */
    private String informerPlace;
    /** Not-null value. */
    private String infoDocument;
    /** Not-null value. */
    private String infoPicture;
    /** Not-null value. */
    private String infoVideo;
    /** Not-null value. */
    private String infoAudio;

    public Crime() {
    }

    public Crime(Long id) {
        this.id = id;
    }

    public Crime(Long id, String crimeInfo, String occurrancePlace, String informerName, String informerPhone, String informerAddress, String informerPlace, String infoDocument, String infoPicture, String infoVideo, String infoAudio) {
        this.id = id;
        this.crimeInfo = crimeInfo;
        this.occurrancePlace = occurrancePlace;
        this.informerName = informerName;
        this.informerPhone = informerPhone;
        this.informerAddress = informerAddress;
        this.informerPlace = informerPlace;
        this.infoDocument = infoDocument;
        this.infoPicture = infoPicture;
        this.infoVideo = infoVideo;
        this.infoAudio = infoAudio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getCrimeInfo() {
        return crimeInfo;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCrimeInfo(String crimeInfo) {
        this.crimeInfo = crimeInfo;
    }

    /** Not-null value. */
    public String getOccurrancePlace() {
        return occurrancePlace;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setOccurrancePlace(String occurrancePlace) {
        this.occurrancePlace = occurrancePlace;
    }

    /** Not-null value. */
    public String getInformerName() {
        return informerName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setInformerName(String informerName) {
        this.informerName = informerName;
    }

    /** Not-null value. */
    public String getInformerPhone() {
        return informerPhone;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setInformerPhone(String informerPhone) {
        this.informerPhone = informerPhone;
    }

    /** Not-null value. */
    public String getInformerAddress() {
        return informerAddress;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setInformerAddress(String informerAddress) {
        this.informerAddress = informerAddress;
    }

    /** Not-null value. */
    public String getInformerPlace() {
        return informerPlace;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setInformerPlace(String informerPlace) {
        this.informerPlace = informerPlace;
    }

    /** Not-null value. */
    public String getInfoDocument() {
        return infoDocument;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setInfoDocument(String infoDocument) {
        this.infoDocument = infoDocument;
    }

    /** Not-null value. */
    public String getInfoPicture() {
        return infoPicture;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setInfoPicture(String infoPicture) {
        this.infoPicture = infoPicture;
    }

    /** Not-null value. */
    public String getInfoVideo() {
        return infoVideo;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setInfoVideo(String infoVideo) {
        this.infoVideo = infoVideo;
    }

    /** Not-null value. */
    public String getInfoAudio() {
        return infoAudio;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setInfoAudio(String infoAudio) {
        this.infoAudio = infoAudio;
    }

}
