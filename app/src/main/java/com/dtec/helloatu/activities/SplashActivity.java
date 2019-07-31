package com.dtec.helloatu.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.TextView;

import com.dtec.helloatu.R;
import com.dtec.helloatu.utilities.MarshMallowPermission;

public class SplashActivity extends Activity {

    SplashActivity activity;
    TextView tvSplash;
    MarshMallowPermission marshMallowPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = this;
        //marshMallowPermission = new MarshMallowPermission(activity);
        tvSplash = findViewById(R.id.tvSplash);
        tvSplash.setText(Html.fromHtml(getString(R.string.splash_text)));
        runSplash();
        //marshMallowPermission.checkPermissions();
    }


    public void runSplash() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 4000);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }


    @Override
    public void onBackPressed() {

        finish();

    }
}
