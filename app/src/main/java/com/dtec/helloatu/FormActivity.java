package com.dtec.helloatu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;

public class FormActivity extends Activity {

    FormActivity activity;
    public Spinner spDivision, spDistrict, spThana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        activity = this;
        spDivision = (Spinner) findViewById(R.id.spDivision);
        spDistrict = (Spinner) findViewById(R.id.spDistrict);
        spThana = (Spinner) findViewById(R.id.spThana);




    }
}
