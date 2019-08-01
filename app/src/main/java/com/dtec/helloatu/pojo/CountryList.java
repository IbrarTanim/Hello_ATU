
package com.dtec.helloatu.pojo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryList {

    @SerializedName("country_list")
    @Expose
    private List<String> countryList = null;

    public List<String> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<String> countryList) {
        this.countryList = countryList;
    }

}
