/*
package com.dtec.helloatu.utilities;

import android.content.Context;
import android.util.Log;


import com.dtec.helloatu.dao.Crime;
import com.dtec.helloatu.manager.DatabaseManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParserMode {

    Context context;
    JSONObject jresponse;
    DatabaseManager databaseManager;
    String JSONValue;
    public ArrayList<Crime> crimeList;

    public static final String TAG_CREATED_AT = "createdAt";
    public static final String TAG_CRIME_CATEGORY = "crimCategory";
    public static final String TAG_CRIME_POSITION = "crimPosition";
    public static final String TAG_CRIME_INFO = "crimeInfo";
    public static final String TAG_DISTRICT = "district";
    public static final String TAG_DISTRICT_INFORMER = "districtInformer";
    public static final String TAG_DIVISION = "division";
    public static final String TAG_INFO_AUDIO = "infoAudio";
    public static final String TAG_INFO_DOCUMENT = "infoDocument";
    public static final String TAG_INFO_PICTURE = "infoPicture";
    public static final String TAG_INFO_VIDEO= "infoVideo";
    public static final String TAG_INFORMER_ADDRESS = "informerAddress";
    public static final String TAG_INFORMER_NAME = "informerName";
    public static final String TAG_INFORMER_PHONE = "informerPhone";
    public static final String TAG_OCCURENCE = "occurrence";
    public static final String TAG_INFORMER_OCCURRENCE = "occurrenceInformer";


    public ParserMode(Context context, JSONObject jresponse) {
        this.context = context;
        this.jresponse = jresponse;
        databaseManager = new DatabaseManager(context);

    }

    public ArrayList<Crime> parser() {
        Log.d("jsonObject", jresponse.toString());
        crimeList = new ArrayList<>();
        try {
            JSONObject mainObj = jresponse.getJSONObject("citizenDetailsResponse");
            if (mainObj != null) {


                JSONArray list = mainObj.getJSONArray("citizenDetail");
                if (list != null) {
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject elem = list.getJSONObject(i);
                        if (elem != null) {

                            Crime crime = new Crime();
                            crime.setCid(jresponse.getString(TAG_CID));
                            client.setFirstName(jresponse.getString(TAG_FIRTS_NAME));
                            client.setMiddleName(jresponse.getString(TAG_MIDDLE_NAME));
                            client.setLastName(jresponse.getString(TAG_LAST_NAME));

                            databaseManager.insertCrime(crime);
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return crimeList;

    }

}
*/
