package com.dtec.helloatu.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictsMain {

@SerializedName("district_list")
@Expose
private List<DistrictList> districtList = null;

public List<DistrictList> getDistrictList() {
return districtList;
}

public void setDistrictList(List<DistrictList> districtList) {
this.districtList = districtList;
}

}
