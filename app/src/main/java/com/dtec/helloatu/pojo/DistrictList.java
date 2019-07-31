
package com.dtec.helloatu.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictList {

    @SerializedName("district_list")
    @Expose
    private List<DistrictMain> districtList = null;

    public List<DistrictMain> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictMain> districtList) {
        this.districtList = districtList;
    }

}
