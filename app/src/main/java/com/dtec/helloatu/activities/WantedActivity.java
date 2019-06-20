package com.dtec.helloatu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dtec.helloatu.R;
import com.dtec.helloatu.dao.Crime;
import com.dtec.helloatu.manager.DatabaseManager;

public class WantedActivity extends Activity {

    WantedActivity activity;
    ImageButton ibtnBackWanted;
    TextView tvValue;
    //DatabaseManager databaseManager;
    //Crime crime;
    int passedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted);

        activity = this;
        //databaseManager = new DatabaseManager(activity);
        //crime = databaseManager.getCrimeById();
        tvValue = findViewById(R.id.tvValue);


        ibtnBackWanted = (ImageButton) findViewById(R.id.ibtnBackWanted);
        ibtnBackWanted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WantedActivity.this, MainActivity.class));
                finish();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            passedPosition = bundle.getInt("positionWanted");
        }
        //Toast.makeText(activity, String.valueOf(passedPosition), Toast.LENGTH_SHORT).show();



    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(activity, MainActivity.class));
        finish();

    }
}
