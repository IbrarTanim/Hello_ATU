package com.dtec.helloatu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class InformationActivity extends Activity {


    InformationActivity activity;
    ImageButton ibtnBackInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        activity = this;

        ibtnBackInformation = (ImageButton) findViewById(R.id.ibtnBackInformation);
        ibtnBackInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InformationActivity.this, MainActivity.class));
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {

        finish();

    }
}
