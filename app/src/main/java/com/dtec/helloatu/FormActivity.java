package com.dtec.helloatu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FormActivity extends Activity implements AdapterView.OnItemSelectedListener {

    FormActivity activity;
    public Spinner spDivision;
    public Spinner spDistrict;
    public Spinner spThana;
    ImageButton ibtnBack;
    public int passedPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        activity = this;

        spDivision = (Spinner) findViewById(R.id.spDivision);
        spDistrict = (Spinner) findViewById(R.id.spDistrict);
        spThana = (Spinner) findViewById(R.id.spThana);
        ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);

        spDivision.setOnItemSelectedListener(this);
        spDistrict.setOnItemSelectedListener(this);
        spThana.setOnItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            passedPosition = bundle.getInt("position");
        }


        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormActivity.this, MainActivity.class);
                intent.putExtra("passedPosition", passedPosition);
                startActivity(intent);
                finish();

            }
        });


        List<String> division = new ArrayList<String>();
        division.add("ঢাকা");
        division.add("চট্টগ্রাম");
        division.add("রাজশাহী");
        division.add("খুলনা");
        division.add("বরিশাল");
        division.add("সিলেট");
        division.add("রংপুর");
        division.add("ময়মনসিংহ");
        ArrayAdapter<String> divisionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, division);
        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDivision.setAdapter(divisionAdapter);

        List<String> district = new ArrayList<String>();
        district.add("গাজীপুর");
        district.add("কুমিল্লা");
        district.add("কক্সবাজার");
        district.add("ফেনী");
        district.add("নোয়াখালী ");
        district.add("লক্ষ্মীপুর");
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, district);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDistrict.setAdapter(districtAdapter);

        List<String> thana = new ArrayList<String>();
        thana.add("কাপাসিয়া ");
        thana.add("কচুয়া");
        thana.add("শ্যামনগর");
        thana.add("কেশবপুর");
        thana.add("লোহাগড়া");
        thana.add("মহেশপুর");
        ArrayAdapter<String> thanaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, thana);
        thanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spThana.setAdapter(thanaAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()) {
            case R.id.spDivision:
                String itemDivision = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + itemDivision, Toast.LENGTH_LONG).show();
                break;


            case R.id.spDistrict:
                String itemDistrict = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + itemDistrict, Toast.LENGTH_LONG).show();
                break;

            case R.id.spThana:
                String itemThana = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + itemThana, Toast.LENGTH_LONG).show();
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onBackPressed() {

        finish();

    }
}
