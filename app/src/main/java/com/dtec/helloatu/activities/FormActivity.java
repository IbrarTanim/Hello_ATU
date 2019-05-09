package com.dtec.helloatu.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtec.helloatu.R;
import com.dtec.helloatu.dao.Crime;
import com.dtec.helloatu.dialogue.DialogNavBarHide;
import com.dtec.helloatu.dialogue.ImageSelectionDialog;
import com.dtec.helloatu.manager.DatabaseManager;
import com.dtec.helloatu.utilities.CustomToast;
import com.dtec.helloatu.utilities.FilePath;
import com.dtec.helloatu.utilities.FileProcessing;
import com.dtec.helloatu.utilities.ImageProcessing;
import com.dtec.helloatu.utilities.InternalStorageContentProvider;
import com.dtec.helloatu.utilities.MarshMallowPermission;
import com.dtec.helloatu.utilities.StaticAccess;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.nostra13.universalimageloader.utils.L;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    FormActivity activity;

    public MarshMallowPermission marshMallowPermission;
    ImageProcessing imageProcessing;
    public Spinner spThana;

    public Spinner spOccurrence;
    public Spinner spOccurrenceInformer;

    public Spinner spDivision;
    public Spinner spDivisionInformer;

    public Spinner spDistrict;
    public Spinner spDistrictInformer;

    ImageButton ibtnBack;
    public int positionForm;
    Button btnSubmit, btnCancel;
    TextView tvDocument, tvVideo, tvAudio;
    EditText etCrimeInfo, etInformerName, etInformerPhone, etInformerAddress;

    ImageView ivCamera;
    String selectedFilePath;
    public ImageSelectionDialog imageSelectionDialog;
    String filePath, imgPath = "";
    String displayName;
    int itemPicFlag = 0;
    Crime crime;

    private String appImagePath = null;
    ImageProcessing imgProc;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public File mFileTemp;
    FileProcessing fileProcessing;

    private static final int PICK_CAMERA_REQUEST = 0x6;
    int currentRequest = -1;
    private static final int PICK_FILE_REQUEST = 0x5;
    private static final int PICK_VIDEO_REQUEST = 0x9;
    private static final int PICK_AUDIO_REQUEST = 0x4;

    public static final int REQUEST_CODE_TAKE_PICTURE = 0x8;
    private static final int SELECT_PICTURE = 0x1;
    private static final int MATERIAL_FILE_PICKER = 0x3;


    int intent_source = 0;
    DatabaseManager databaseManager;


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

    String documentName;
    String videoName;
    String audioName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        activity = this;
        marshMallowPermission = new MarshMallowPermission(activity);
        databaseManager = new DatabaseManager(activity);
        imageProcessing = new ImageProcessing(activity);
        crime = new Crime();

        spDivision = (Spinner) findViewById(R.id.spDivision);
        spDivisionInformer = (Spinner) findViewById(R.id.spDivisionInformer);
        spDistrictInformer = (Spinner) findViewById(R.id.spDistrictInformer);
        spDistrict = (Spinner) findViewById(R.id.spDistrict);
        spThana = (Spinner) findViewById(R.id.spThana);
        spOccurrenceInformer = (Spinner) findViewById(R.id.spOccurrenceInformer);
        spOccurrence = (Spinner) findViewById(R.id.spOccurrence);
        ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);

        tvDocument = findViewById(R.id.tvDocument);
        tvVideo = findViewById(R.id.tvVideo);
        tvAudio = findViewById(R.id.tvAudio);

        etCrimeInfo = findViewById(R.id.etCrimeInfo);
        etInformerName = findViewById(R.id.etInformerName);
        etInformerPhone = findViewById(R.id.etInformerPhone);
        etInformerAddress = findViewById(R.id.etInformerAddress);

        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ibDocument = findViewById(R.id.ibDocument);
        ibCamera = findViewById(R.id.ibCamera);
        ibVideo = findViewById(R.id.ibVideo);
        ibAudio = findViewById(R.id.ibAudio);

        imgProc = new ImageProcessing(activity);
        appImagePath = imgProc.getImageDir();


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
            positionForm = bundle.getInt("positionForm");
        }

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToPrevious();
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
                enterStorage(getString(R.string.type_docs), PICK_FILE_REQUEST);
                break;
            case R.id.ibCamera:

                imageSelectionDialog = new ImageSelectionDialog(activity, activity, itemPicFlag);
                DialogNavBarHide.navBarHide(activity, imageSelectionDialog);

                break;

            case R.id.ibVideo:
                enterStorage(getString(R.string.type_video), PICK_VIDEO_REQUEST);
                break;

            case R.id.ibAudio:
                enterStorage(getString(R.string.type_audio), PICK_AUDIO_REQUEST);
                break;


            case R.id.btnSubmit:

                if (etCrimeInfo.getText().length() > 0) {
                    crime.setCrimeInfo(etCrimeInfo.getText().toString());
                    crime.setOccurrence(spOccurrence.getSelectedItemPosition());
                    crime.setInformerName(etInformerName.getText().toString());
                    crime.setInformerPhone(etInformerPhone.getText().toString());
                    crime.setInformerAddress(etInformerAddress.getText().toString());
                    crime.setInfoDocument(documentName);
                    crime.setInfoPicture(checkGettingImage(imgPath));
                    crime.setInfoVideo(videoName);
                    crime.setInfoAudio(audioName);
                    crime.setOccurrence(spOccurrence.getSelectedItemPosition());
                    crime.setOccurrenceInformer(spOccurrenceInformer.getSelectedItemPosition());
                    crime.setDivision(spDivision.getSelectedItemPosition());
                    crime.setDivisionInformer(spDivisionInformer.getSelectedItemPosition());
                    crime.setDistrict(spDistrict.getSelectedItemPosition());
                    crime.setDistrictInformer(spDistrictInformer.getSelectedItemPosition());
                    imageProcessing.setImageWith_loader(ivCamera, imgPath);
                    databaseManager.insertCrime(crime);
                    backToPrevious();
                    Toast.makeText(activity, "Form filled up Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.inform_terrorism), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnCancel:
                backToPrevious();
                break;

        }
    }


    // Opening Image Cropper (Transparent)
    public void openCropper(Uri uri) {

        com.theartofdev.edmodo.cropper.CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .start(activity);
    }

    // load image from camera by Rokan
    public void loadImageCamera() {
        // flag for using item or task
        File sdCardDirectory = new File(Environment.getExternalStorageDirectory() + appImagePath);
        if (!sdCardDirectory.exists()) {
            sdCardDirectory.mkdirs();
        }
        String state1 = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state1)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory() + appImagePath, TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir() + appImagePath, TEMP_PHOTO_FILE_NAME);
        }
        //musicControl = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            Uri mImageCaptureUri = null;
            String state2 = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state2)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            } else {
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);

        } catch (ActivityNotFoundException e) {
        }

        intent_source = 2;
    }


    // load image from Gallery by Rokan
    public void loadImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

        intent_source = 1;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        imageSelection(requestCode, resultCode, data);

        switch (requestCode) {

            case PICK_FILE_REQUEST:
                documentName = resultActivity(resultCode, data, tvDocument);
                break;
            case PICK_VIDEO_REQUEST:
                videoName = resultActivity(resultCode, data, tvVideo);
                break;
            case PICK_AUDIO_REQUEST:
                audioName = resultActivity(resultCode, data, tvAudio);
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public String resultActivity(int resultCode, Intent data, TextView textView) {

        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            //tvDocument.setText(path);


            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = activity.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        textView.setText(displayName);
                        textView.setVisibility(View.VISIBLE);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }
        }

        return displayName;
    }


    public void imageSelection(int requestCode, int resultCode, Intent data) {
        fileProcessing = new FileProcessing(activity);
        Bitmap widgetImage = null;
        if (resultCode == RESULT_OK) {

            // Load Image from Gallery
            if (requestCode == SELECT_PICTURE && intent_source == 1) {
                openCropper(data.getData());
            }

            // load image from Camera
            if (requestCode == REQUEST_CODE_TAKE_PICTURE && intent_source == 2) {
                openCropper(Uri.fromFile(new File(mFileTemp.getAbsolutePath())));

            }

            // Load image after Cropping (Transparent)
            if (requestCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                com.theartofdev.edmodo.cropper.CropImage.ActivityResult result = com.theartofdev.edmodo.cropper.CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                setImagePro(bitmap);


            } else if (resultCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(activity, "Crop Error", Toast.LENGTH_SHORT).show();
            }


            // Getting file with Material File picker
            if (requestCode == MATERIAL_FILE_PICKER) {
                filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                int intFileSize = fileProcessing.fileSize(filePath);
                if (intFileSize <= StaticAccess.TAG_SOUND_FILE_SIZE) {

                } else {
                    CustomToast.t(activity, getResources().getString(R.string.notSupported));
                }

            }

        }

        setImagePro(widgetImage);
        intent_source = 0;

    }


    public void setImagePro(Bitmap bitmap) {
        //scale bitmap
        if (bitmap != null) {
            Bitmap b = (bitmap);
            ivCamera.setVisibility(View.VISIBLE);
            imgPath = imgProc.imageSave(b);
            imgProc.setImageWith_loader(ivCamera, imgPath);
            b.recycle();
        }

    }

    public void enterStorage(String type, int FLAG) {
        Intent intent = new Intent();
        intent.setType(type);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload"), FLAG);
    }

    String checkGettingImage(String imgPath) {
        String imgFilePath;
        if (imgPath.length() > 0) {
            imgFilePath = imgPath;
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.atu);
//            imgProc.imageSave(bitmap);
            imgFilePath = imageProcessing.imageSave(bitmap);
        }
        return imgFilePath;
    }

    public void backToPrevious() {
        Intent intent = new Intent(FormActivity.this, MainActivity.class);
        intent.putExtra("positionForm", positionForm);
        //intent.putExtra("currentCrime", crime.getId());
        startActivity(intent);
        finish();
    }

}
