
package com.dtec.helloatu.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MohanogorList {

    @SerializedName("mohanogor")
    @Expose
    private String mohanogor;
    @SerializedName("thana_list")
    @Expose
    private List<String> thanaList = null;

    public String getMohanogor() {
        return mohanogor;
    }

    public void setMohanogor(String mohanogor) {
        this.mohanogor = mohanogor;
    }

    public List<String> getThanaList() {
        return thanaList;
    }

    public void setThanaList(List<String> thanaList) {
        this.thanaList = thanaList;
    }

}
