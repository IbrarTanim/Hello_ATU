
package com.dtec.helloatu.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictMain {

    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("thana_list")
    @Expose
    private List<String> thanaList = null;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<String> getThanaList() {
        return thanaList;
    }

    public void setThanaList(List<String> thanaList) {
        this.thanaList = thanaList;
    }

}