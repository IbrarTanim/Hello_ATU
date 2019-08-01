
package com.dtec.helloatu.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mohanogor {

    @SerializedName("mohanogor_list")
    @Expose
    private List<MohanogorMain> mohanogorMain = null;

    public List<MohanogorMain> getMohanogorMain() {
        return mohanogorMain;
    }

    public void setMohanogorMain(List<MohanogorMain> mohanogorMain) {
        this.mohanogorMain = mohanogorMain;
    }

}
