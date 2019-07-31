
package com.dtec.helloatu.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mohanogor {

    @SerializedName("mohanogor_list")
    @Expose
    private List<MohanogorList> mohanogorList = null;

    public List<MohanogorList> getMohanogorList() {
        return mohanogorList;
    }

    public void setMohanogorList(List<MohanogorList> mohanogorList) {
        this.mohanogorList = mohanogorList;
    }

}
