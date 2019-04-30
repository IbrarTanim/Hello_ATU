package com.dtec.helloatu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.TextView;

public class SplashActivity extends Activity {

    SplashActivity activity;
    TextView tvSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = this;
        tvSplash = findViewById(R.id.tvSplash);
        tvSplash.setText(Html.fromHtml(getString(R.string.splash_text)));

        runSplash();

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
        }, 2000);

    }


    @Override
    public void onBackPressed() {

        finish();

    }
}
