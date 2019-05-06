package com.dtec.helloatu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dtec.helloatu.R;
import com.dtec.helloatu.customUI.CircularImageView;

public class InformationActivity extends BaseActivity implements View.OnClickListener {


    InformationActivity activity;
    ImageButton ibtnBackInformation;

    CircularImageView civUserManual, civUnitDetails, civNewsFeed, civAnonymousChat;
    TextView tvUserManual, tvUnitDetails, tvNewsFeed;

    boolean isButtonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        activity = this;


        civUserManual = (CircularImageView) findViewById(R.id.civUserManual);
        civUnitDetails = (CircularImageView) findViewById(R.id.civUnitDetails);
        civNewsFeed = (CircularImageView) findViewById(R.id.civNewsFeed);
        civAnonymousChat = (CircularImageView) findViewById(R.id.civAnonymousChat);
        ibtnBackInformation = (ImageButton) findViewById(R.id.ibtnBackInformation);

        tvUserManual = (TextView) findViewById(R.id.tvUserManual);
        tvUnitDetails = (TextView) findViewById(R.id.tvUnitDetails);
        tvNewsFeed = (TextView) findViewById(R.id.tvNewsFeed);


        civUserManual.setOnClickListener(this);
        civUnitDetails.setOnClickListener(this);
        civNewsFeed.setOnClickListener(this);
        civAnonymousChat.setOnClickListener(this);
        ibtnBackInformation.setOnClickListener(this);


    }


    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.civUserManual:

                if (isButtonClicked) {
                    civUserManual.setImageResource(R.drawable.ic_up_arrow);
                    tvUserManual.setVisibility(View.VISIBLE);
                    isButtonClicked = false;
                } else {
                    civUserManual.setImageResource(R.drawable.ic_down_arrow);
                    tvUserManual.setVisibility(View.GONE);
                    isButtonClicked = true;
                }


                break;
            case R.id.civUnitDetails:

                if (isButtonClicked) {
                    civUnitDetails.setImageResource(R.drawable.ic_up_arrow);
                    tvUnitDetails.setVisibility(View.VISIBLE);
                    isButtonClicked = false;
                } else {
                    civUnitDetails.setImageResource(R.drawable.ic_down_arrow);
                    tvUnitDetails.setVisibility(View.GONE);
                    isButtonClicked = true;
                }

                break;
            case R.id.civNewsFeed:

                if (isButtonClicked) {
                    civNewsFeed.setImageResource(R.drawable.ic_up_arrow);
                    tvNewsFeed.setVisibility(View.VISIBLE);
                    isButtonClicked = false;
                } else {
                    civNewsFeed.setImageResource(R.drawable.ic_down_arrow);
                    tvNewsFeed.setVisibility(View.GONE);
                    isButtonClicked = true;
                }

                break;

            case R.id.civAnonymousChat:

                if (isButtonClicked) {
                    civAnonymousChat.setImageResource(R.drawable.ic_up_arrow);
                    isButtonClicked = false;
                } else {
                    civAnonymousChat.setImageResource(R.drawable.ic_down_arrow);
                    isButtonClicked = true;
                }


                break;

            case R.id.ibtnBackInformation:
                startActivity(new Intent(InformationActivity.this, MainActivity.class));
                finish();
                break;


        }

    }
}
