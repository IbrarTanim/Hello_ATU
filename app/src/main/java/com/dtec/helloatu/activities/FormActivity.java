package com.dtec.helloatu.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtec.helloatu.R;
import com.dtec.helloatu.utilities.FilePath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FormActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    FormActivity activity;
    public Spinner spDivision;
    public Spinner spDivisionInformer;

    public Spinner spDistrict;
    public Spinner spDistrictInformer;
    public Spinner spThana;
    public Spinner spOccurrenceInformer;
    public Spinner spOccurrence;
    ImageButton ibtnBack;
    public int passedPosition;
    Button btnSubmit, btnCancel;
    TextView tvDocument;
    ImageView ivCamera;
    String selectedFilePath;

    private static final int PICK_FILE_REQUEST = 0x1;
    private static final int PICK_CAMERA_REQUEST = 0x2;
    private static final int PICK_VIDEO_REQUEST = 0x3;
    private static final int PICK_AUDIO_REQUEST = 0x4;

    ImageButton ibDocument, ibCamera, ibVideo, ibAudio;

    ArrayAdapter<String> occurrenceAdapter;
    ArrayAdapter<String> divisionAdapter;
    ArrayAdapter<String> districtAdapter;

    List<String> division;
    List<String> occurrence;
    List<String> thanaDhaka;

    List<String> dstDhaka;
    List<String> dstChittagong;
    List<String> dstSylhet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        activity = this;

        spDivision = (Spinner) findViewById(R.id.spDivision);
        spDivisionInformer = (Spinner) findViewById(R.id.spDivisionInformer);
        spDistrictInformer = (Spinner) findViewById(R.id.spDistrictInformer);
        spDistrict = (Spinner) findViewById(R.id.spDistrict);
        spThana = (Spinner) findViewById(R.id.spThana);
        spOccurrenceInformer = (Spinner) findViewById(R.id.spOccurrenceInformer);
        spOccurrence = (Spinner) findViewById(R.id.spOccurrence);
        ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);

        tvDocument = findViewById(R.id.tvDocument);
        ivCamera = findViewById(R.id.ivCamera);

        ibDocument = findViewById(R.id.ibDocument);
        ibCamera = findViewById(R.id.ibCamera);
        ibVideo = findViewById(R.id.ibVideo);
        ibAudio = findViewById(R.id.ibAudio);


        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        ibDocument.setOnClickListener(this);
        ibCamera.setOnClickListener(this);
        ibVideo.setOnClickListener(this);
        ibAudio.setOnClickListener(this);

        spDivision.setOnItemSelectedListener(this);
        spDivisionInformer.setOnItemSelectedListener(this);
        spDistrict.setOnItemSelectedListener(this);
        spDistrictInformer.setOnItemSelectedListener(this);
        spThana.setOnItemSelectedListener(this);
        spOccurrenceInformer.setOnItemSelectedListener(this);
        spOccurrence.setOnItemSelectedListener(this);

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


        division = new ArrayList<String>();
        division.add(getString(R.string.division_selection));
        division.add(getString(R.string.div_dhaka));
        division.add(getString(R.string.div_chittagong));
        division.add(getString(R.string.div_rajshai));
        division.add(getString(R.string.div_khulna));
        division.add(getString(R.string.div_barishal));
        division.add(getString(R.string.div_sylhet));
        division.add(getString(R.string.div_rongpur));
        division.add(getString(R.string.div_mymensing));

        divisionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, division);
        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDivision.setAdapter(divisionAdapter);


        occurrence = new ArrayList<String>();
        occurrence.add(getString(R.string.mohanogor_district_bdout));
        occurrence.add(getString(R.string.mohanogor));
        occurrence.add(getString(R.string.district));
        occurrence.add(getString(R.string.bdout));

        occurrenceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, occurrence);
        occurrenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOccurrence.setAdapter(occurrenceAdapter);
        spOccurrenceInformer.setAdapter(occurrenceAdapter);


        dstDhaka = new ArrayList<String>();
        dstDhaka.add(getString(R.string.district_selection));
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

        thanaDhaka = new ArrayList<String>();
        thanaDhaka.add(getString(R.string.country_selection));
        thanaDhaka.add(getString(R.string.india));
        thanaDhaka.add(getString(R.string.america));
        thanaDhaka.add(getString(R.string.canada));
        thanaDhaka.add(getString(R.string.austrelia));
        thanaDhaka.add(getString(R.string.japan));
        thanaDhaka.add(getString(R.string.russia));
        thanaDhaka.add(getString(R.string.china));
        thanaDhaka.add(getString(R.string.indoneshia));
        thanaDhaka.add(getString(R.string.srilanka));
        thanaDhaka.add(getString(R.string.pakisthan));
        thanaDhaka.add(getString(R.string.katar));


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


        ArrayAdapter<String> thanaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, thanaDhaka);
        thanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spThana.setAdapter(thanaAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()) {

            case R.id.spOccurrence:
                String itemOccurrence = parent.getItemAtPosition(position).toString();

                if (itemOccurrence == getString(R.string.mohanogor)) {
                    divisionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, division);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivision.setAdapter(divisionAdapter);

                    spDivision.setVisibility(View.VISIBLE);
                    spDistrict.setVisibility(View.GONE);
                    spThana.setVisibility(View.GONE);


                } else if (itemOccurrence == getString(R.string.district)) {
                    divisionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dstDhaka);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivision.setAdapter(divisionAdapter);
                    spDivision.setVisibility(View.VISIBLE);
                    spDistrict.setVisibility(View.GONE);
                    spThana.setVisibility(View.GONE);

                } else if (itemOccurrence == getString(R.string.bdout)) {
                    divisionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, thanaDhaka);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivision.setAdapter(divisionAdapter);
                    spDivision.setVisibility(View.VISIBLE);
                    spDistrict.setVisibility(View.GONE);
                    spThana.setVisibility(View.GONE);
                }


                break;


            case R.id.spDivision:
                String itemDivision = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + itemDivision, Toast.LENGTH_LONG).show();

                if (itemDivision == getString(R.string.div_dhaka)) {
                    districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dstDhaka);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
                    spDistrict.setVisibility(View.VISIBLE);
                    spDivision.setVisibility(View.VISIBLE);
                    spThana.setVisibility(View.GONE);


                } else if (itemDivision == getString(R.string.div_chittagong)) {
                    districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dstChittagong);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
                    spDistrict.setVisibility(View.VISIBLE);
                    spDivision.setVisibility(View.VISIBLE);
                    spThana.setVisibility(View.GONE);

                } else if (itemDivision == getString(R.string.div_sylhet)) {
                    districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dstSylhet);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
                    spDistrict.setVisibility(View.VISIBLE);
                    spDivision.setVisibility(View.VISIBLE);
                    spThana.setVisibility(View.GONE);
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


            case R.id.spOccurrenceInformer:
                String itemOccurrenceInformer = parent.getItemAtPosition(position).toString();

                if (itemOccurrenceInformer == getString(R.string.mohanogor)) {
                    divisionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, division);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivisionInformer.setAdapter(divisionAdapter);

                    spDivisionInformer.setVisibility(View.VISIBLE);
                    spDistrictInformer.setVisibility(View.GONE);
                    // spThanaInformer.setVisibility(View.GONE);


                } else if (itemOccurrenceInformer == getString(R.string.district)) {
                    divisionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dstDhaka);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivisionInformer.setAdapter(divisionAdapter);
                    spDivisionInformer.setVisibility(View.VISIBLE);
                    spDistrictInformer.setVisibility(View.GONE);
                    //spThana.setVisibility(View.GONE);

                } else if (itemOccurrenceInformer == getString(R.string.bdout)) {
                    divisionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, thanaDhaka);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivisionInformer.setAdapter(divisionAdapter);
                    spDivisionInformer.setVisibility(View.VISIBLE);
                    spDistrictInformer.setVisibility(View.GONE);
                    //spThana.setVisibility(View.GONE);
                }


                break;

            case R.id.spDivisionInformer:
                String itemDivisionInformer = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + itemDivision, Toast.LENGTH_LONG).show();

                if (itemDivisionInformer == getString(R.string.div_dhaka)) {
                    districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dstDhaka);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrictInformer.setAdapter(districtAdapter);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    spDivisionInformer.setVisibility(View.VISIBLE);
                    //spThana.setVisibility(View.GONE);


                } else if (itemDivisionInformer == getString(R.string.div_chittagong)) {
                    districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dstChittagong);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrictInformer.setAdapter(districtAdapter);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    spDivisionInformer.setVisibility(View.VISIBLE);
                    //spThana.setVisibility(View.GONE);

                } else if (itemDivisionInformer == getString(R.string.div_sylhet)) {
                    districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dstSylhet);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrictInformer.setAdapter(districtAdapter);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    spDivisionInformer.setVisibility(View.VISIBLE);
                    //spThana.setVisibility(View.GONE);
                }

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

    Intent intent;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.ibDocument:

                intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
                break;

            case R.id.ibCamera:

                intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose picture to Upload.."), PICK_CAMERA_REQUEST);
                break;

            case R.id.ibVideo:
                intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_VIDEO_REQUEST);
                break;

            case R.id.ibAudio:
                intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_AUDIO_REQUEST);
                break;


            case R.id.btnSubmit:
                Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCancel:
                Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show();
                break;


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {


            Uri selectedFileUri = data.getData();
            selectedFilePath = FilePath.getActualPath(this, selectedFileUri);
            Uri uriFromPath = Uri.fromFile(new File(selectedFilePath));


            // path for file
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    return;
                }
                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    tvDocument.setText("Document URL:" + selectedFilePath);

                } else {
                    Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }


            // path for camera
            if (requestCode == PICK_CAMERA_REQUEST) {
                if (data == null) {
                    return;
                }
                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    //tvDocument.setText(selectedFilePath);
                    ivCamera.setImageURI(uriFromPath);

                } else {
                    Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }


            // path for Video
            if (requestCode == PICK_VIDEO_REQUEST) {
                if (data == null) {
                    return;
                }
                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    tvDocument.setText("Video URL: " + selectedFilePath);

                } else {
                    Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }


            // path for Audio
            if (requestCode == PICK_AUDIO_REQUEST) {
                if (data == null) {
                    return;
                }
                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    tvDocument.setText("Audio URL: " + selectedFilePath);

                } else {
                    Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }


}
