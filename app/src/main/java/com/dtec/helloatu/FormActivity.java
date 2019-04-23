package com.dtec.helloatu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FormActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    FormActivity activity;
    public Spinner spDivision;
    public Spinner spDistrict;
    public Spinner spThana;
    ImageButton ibtnBack;
    public int passedPosition;
    Button btnSubmit, btnCancel;
    ArrayAdapter<String> districtAdapter;

    List<String> dstDhaka;
    List<String> dstChittagong;
    List<String> dstSylhet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        activity = this;

        spDivision = (Spinner) findViewById(R.id.spDivision);
        spDistrict = (Spinner) findViewById(R.id.spDistrict);
        spThana = (Spinner) findViewById(R.id.spThana);
        ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

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
        division.add(getString(R.string.div_dhaka));
        division.add(getString(R.string.div_chittagong));
        division.add(getString(R.string.div_rajshai));
        division.add(getString(R.string.div_khulna));
        division.add(getString(R.string.div_barishal));
        division.add(getString(R.string.div_sylhet));
        division.add(getString(R.string.div_rongpur));
        division.add(getString(R.string.div_mymensing));
        ArrayAdapter<String> divisionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, division);
        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDivision.setAdapter(divisionAdapter);

        dstDhaka = new ArrayList<String>();
        dstDhaka.add(getString(R.string.div_dhaka));
        dstDhaka.add(getString(R.string.dst_gazipur));
        dstDhaka.add(getString(R.string.dst_faridpur));
        dstDhaka.add(getString(R.string.dst_shariatpur));
        dstDhaka.add(getString(R.string.dst_madaripur));
        dstDhaka.add(getString(R.string.dst_gopalgonj));
        dstDhaka.add(getString(R.string.dst_kishorgonj));
        dstDhaka.add(getString(R.string.dst_manikgonj));
        dstDhaka.add(getString(R.string.dst_munsigonj));
        dstDhaka.add(getString(R.string.dst_narayangonj));
        dstDhaka.add(getString(R.string.dst_norsingdi));
        dstDhaka.add(getString(R.string.dst_rajbari));
        dstDhaka.add(getString(R.string.dst_tangail));

        dstChittagong = new ArrayList<String>();
        dstChittagong.add(getString(R.string.div_chittagong));
        dstChittagong.add(getString(R.string.dst_coxbazar));
        dstChittagong.add(getString(R.string.dst_khagrachori));
        dstChittagong.add(getString(R.string.dst_rangamati));
        dstChittagong.add(getString(R.string.dst_bandorban));
        dstChittagong.add(getString(R.string.dst_feni));
        dstChittagong.add(getString(R.string.dst_noakhali));
        dstChittagong.add(getString(R.string.dst_lakshmipur));
        dstChittagong.add(getString(R.string.dst_chandpur));
        dstChittagong.add(getString(R.string.dst_comilla));
        dstChittagong.add(getString(R.string.dst_bbaria));


        dstSylhet = new ArrayList<String>();
        dstSylhet.add(getString(R.string.div_sylhet));
        dstSylhet.add(getString(R.string.dst_sunamgonj));
        dstSylhet.add(getString(R.string.dst_hobigonj));
        dstSylhet.add(getString(R.string.dst_mowlobibazar));






        List<String> district = new ArrayList<String>();

        district.add("কুমিল্লা");
        district.add("কক্সবাজার");
        district.add("ফেনী");
        district.add("নোয়াখালী ");
        district.add("লক্ষ্মীপুর");





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

                if (itemDivision == getString(R.string.div_dhaka)) {
                    districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dstDhaka);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);

                } else if (itemDivision == getString(R.string.div_chittagong)) {
                    districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dstChittagong);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
                }

                else if (itemDivision == getString(R.string.div_sylhet)) {
                    districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dstSylhet);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
                }




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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCancel:
                Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
