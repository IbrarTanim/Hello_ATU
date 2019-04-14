package com.dtec.helloatu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends Activity {

    MainActivity activity;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         activity = this;
         recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


    }
}
