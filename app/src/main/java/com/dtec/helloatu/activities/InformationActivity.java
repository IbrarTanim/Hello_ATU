package com.dtec.helloatu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dtec.helloatu.R;
import com.dtec.helloatu.customUI.CircularImageView;

public class InformationActivity extends Activity implements View.OnClickListener {


    InformationActivity activity;
    ImageButton ibtnBackInformation;

    CircularImageView civUserManual, civUnitDetails, civNewsFeed, civAnonymousChat;
    TextView tvUserManual, tvUnitDetails, tvNewsFeed;
    boolean isUserManualClicked = false;
    boolean isUnitDetailsClicked = false;
    boolean isNewsFeedClicked = false;
    boolean isAnonymousChatClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        activity = this;

        civUserManual = findViewById(R.id.civUserManual);
        civUnitDetails = findViewById(R.id.civUnitDetails);
        civNewsFeed = findViewById(R.id.civNewsFeed);
        civAnonymousChat = findViewById(R.id.civAnonymousChat);
        ibtnBackInformation = findViewById(R.id.ibtnBackInformation);

        tvUserManual = findViewById(R.id.tvUserManual);
        tvUnitDetails = findViewById(R.id.tvUnitDetails);
        tvNewsFeed = findViewById(R.id.tvNewsFeed);


        civUserManual.setOnClickListener(this);
        civUnitDetails.setOnClickListener(this);
        civNewsFeed.setOnClickListener(this);
        civAnonymousChat.setOnClickListener(this);
        ibtnBackInformation.setOnClickListener(this);

        isUserManualClicked = true;
        isUnitDetailsClicked = true;
        isNewsFeedClicked = true;
        isAnonymousChatClicked = true;


    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(InformationActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.civUserManual:
                if (isUserManualClicked) {
                    civUserManual.setImageResource(R.drawable.ic_up_arrow);
                    tvUserManual.setVisibility(View.VISIBLE);
                    isUserManualClicked = false;
                } else {
                    civUserManual.setImageResource(R.drawable.ic_down_arrow);
                    tvUserManual.setVisibility(View.GONE);
                    isUserManualClicked = true;
                }


                break;
            case R.id.civUnitDetails:
                if (isUnitDetailsClicked) {
                    civUnitDetails.setImageResource(R.drawable.ic_up_arrow);
                    tvUnitDetails.setVisibility(View.VISIBLE);
                    isUnitDetailsClicked = false;
                } else {
                    civUnitDetails.setImageResource(R.drawable.ic_down_arrow);
                    tvUnitDetails.setVisibility(View.GONE);
                    isUnitDetailsClicked = true;
                }

                break;
            case R.id.civNewsFeed:
                if (isNewsFeedClicked) {
                    civNewsFeed.setImageResource(R.drawable.ic_up_arrow);
                    tvNewsFeed.setVisibility(View.VISIBLE);
                    isNewsFeedClicked = false;
                } else {
                    civNewsFeed.setImageResource(R.drawable.ic_down_arrow);
                    tvNewsFeed.setVisibility(View.GONE);
                    isNewsFeedClicked = true;
                }

                break;

            case R.id.civAnonymousChat:
                if (isAnonymousChatClicked) {
                    civAnonymousChat.setImageResource(R.drawable.ic_up_arrow);
                    isAnonymousChatClicked = false;
                } else {
                    civAnonymousChat.setImageResource(R.drawable.ic_down_arrow);
                    isAnonymousChatClicked = true;
                }


                break;

            case R.id.ibtnBackInformation:
                startActivity(new Intent(InformationActivity.this, MainActivity.class));
                finish();
                break;


        }

    }
}
