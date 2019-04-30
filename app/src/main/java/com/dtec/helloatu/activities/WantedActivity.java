package com.dtec.helloatu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.dtec.helloatu.R;

public class WantedActivity extends Activity {

    WantedActivity activity;
    ImageButton ibtnBackWanted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted);

        activity = this;

        ibtnBackWanted = (ImageButton)findViewById(R.id.ibtnBackWanted);
        ibtnBackWanted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WantedActivity.this, MainActivity.class));
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {

        finish();

    }
}
