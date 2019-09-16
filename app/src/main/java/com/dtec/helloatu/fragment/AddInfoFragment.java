package com.dtec.helloatu.fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.dtec.helloatu.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.dtec.helloatu.activities.FragmentBaseActivity;
import com.dtec.helloatu.activities.MainActivity;
import com.dtec.helloatu.dao.Crime;
import com.dtec.helloatu.dialogue.DialogNavBarHide;
import com.dtec.helloatu.dialogue.ImageSelectionDialog;
import com.dtec.helloatu.manager.DatabaseManager;
import com.dtec.helloatu.pojo.CountryList;
import com.dtec.helloatu.pojo.DistrictList;
import com.dtec.helloatu.pojo.DistrictMain;
import com.dtec.helloatu.pojo.Mohanogor;
import com.dtec.helloatu.pojo.MohanogorMain;
import com.dtec.helloatu.utilities.AppController;
import com.dtec.helloatu.utilities.CustomToast;
import com.dtec.helloatu.utilities.FileProcessing;
import com.dtec.helloatu.utilities.ImageProcessing;
import com.dtec.helloatu.utilities.InternetConnectionCheck;
import com.dtec.helloatu.utilities.MarshMallowPermission;
import com.dtec.helloatu.utilities.StaticAccess;
import com.dtec.helloatu.utilities.VolleyMultipartRequest;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_AUDIO_REQUEST;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_FILE_REQUEST;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_VIDEO_REQUEST;
import static com.dtec.helloatu.utilities.StaticAccess.ROOT_URL_ATU;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_APP_AUTH_TOKEN;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_CRIME_TYPE;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_CRIME_INFO;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_DISTRICT_OR_METROPOLITAN;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFORMER_ADDRESS;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFORMER_DISTRICT_OR_METROPOLITAN;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFORMER_DIVISION_OR_COUNTRY;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFORMER_EMAIL;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFORMER_NAME;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFORMER_NID;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFORMER_PHONE;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFORMER_THANA;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFO_AUDIO;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFO_AUDIO_NAME;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFO_DOCUMENT;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFO_DOCUMENT_NAME;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFO_PICTURE;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFO_PICTURE_NAME;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFO_VIDEO;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_INFO_VIDEO_NAME;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_DIVISION_OR_COUNTRY;
import static com.dtec.helloatu.utilities.StaticAccess.TAG_THANA;

public class AddInfoFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    FragmentBaseActivity activity;
    public MarshMallowPermission marshMallowPermission;
    ImageProcessing imageProcessing;
    public Spinner spDimout;
    public Spinner spDimoutInformer;
    public Spinner spDistrict;
    public Spinner spDistrictInformer;
    ScrollView scrollViewAddInfo;
    public Spinner spThana;
    public Spinner spThanaInformer;
    Button btnSubmit, btnCancel;
    EditText etCrimeInfo, etInformerName, etInformerPhone, etInformerAddress, etInformerEmail, etInformerNID;

    float videoFileSize = 0;
    float audioFileSize = 0;
    float documentFileSize = 0;

    String audioFile;
    String documentFile;
    String imagefile;
    String videofile;
    //Map<String, String> params = new HashMap<String, String>();


    Editable charSequence;
    PDFView pdfView;
    LinearLayout llPDFView;
    public LinearLayout llDocument, llCamera, llVideo, llAudio;
    public TextView tvDocument, tvCamera, tvVideo, tvAudio, tvCrimeTitle;
    TextView tvDocumentShowHide, tvPicShowHide, tvVideoPlayStop, tvAudioPlayStop, tvAudioClear, tvPicClear, tvDocumentClear, tvVideoClear;
    FileProcessing fileProcessing;
    List<DistrictMain> listDistrictMain;
    List<MohanogorMain> mohanogorMains;

    //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //String emailPattern = ("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9\\-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //String emailPattern = "[a-zA-Z0-9+._\\%-+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9-]{0,64}(.[a-zA-Z0-9][a-zA-Z0-9-]{0,25})+";
    //String emailPattern = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    //String emailPattern = "[a-zA-Z0-9+._%-+]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "." + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+";


    String informerEmailValue;
    public ProgressDialog pDialog;
    public ImageView ivCamera;
    String imgPath = "";
    String byteConvertedImage;
    String videoCheck;
    Crime crime;
    String strFile;
    ImageProcessing imgProc;

    String displayDocumentName;
    String displayVideoName;
    String displayAudioName;

    String displayDocumentFileName;
    String displayVideoFileName;
    String displayAudioFileName;
    byte[] displayByte;

    Uri pdfUrl;
    public int itemPicFlag = 0;
    int position = -1;
    String categoryName;

    Integer pageNumber = 0;

    Uri uriVideo;
    Uri uriAudio;

    String jsonDistrict = "districts.json";
    String jsonMohanogor = "mohanogor.json";
    String jsonCountry = "country.json";

    public Bitmap bitmapImage;
    public byte[] bytesVideo;
    public byte[] bytesAudio;
    public byte[] bytesDocument;

    Uri uriDataVideo;
    Uri uriDataAudio;
    Uri uriDataDocument;
    Uri uriDataImage;

    String itemOccurrence;
    String itemOccurrenceInformer;

    boolean isDocumentShow = false;
    boolean isPicsShow = false;
    boolean isVideoPlay = false;
    boolean isAudioPlay = false;
    boolean isVideoClearClicked = false;
    boolean isAudioClearClicked = false;
    boolean isDocumentClearClicked = false;
    boolean isImageClearClicked = false;

    VideoView videoView;
    public MediaPlayer mediaPlayer;
    DatabaseManager databaseManager;
    ImageButton ibDocument, ibCamera, ibVideo, ibAudio;

    ArrayAdapter<String> occurrenceAdapter;
    ArrayAdapter<String> districtAdapter;
    ArrayAdapter<String> thanaAdapter;
    ArrayAdapter<String> mohanogorThanaAdapter;

    List<String> occurrence;
    List<String> listDistrict;
    List<String> listDistrictThana;
    List<String> listMohanogor;
    List<String> listCountry;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_info, container, false);
        activity = (FragmentBaseActivity) getActivity();

        marshMallowPermission = new MarshMallowPermission(activity);
        databaseManager = new DatabaseManager(activity);
        imageProcessing = new ImageProcessing(activity);
        imgProc = new ImageProcessing(activity);
        crime = new Crime();
        activity.appImagePath = imgProc.getImageDir();
        spDistrict = view.findViewById(R.id.spDistrict);
        spDistrictInformer = view.findViewById(R.id.spDistrictInformer);
        spThanaInformer = view.findViewById(R.id.spThanaInformer);
        spThana = view.findViewById(R.id.spThana);
        spDimoutInformer = view.findViewById(R.id.spDimoutInformer);
        spDimout = view.findViewById(R.id.spDimout);
        ivCamera = view.findViewById(R.id.ivCamera);
        tvCrimeTitle = view.findViewById(R.id.tvCrimeTitle);
        tvDocument = view.findViewById(R.id.tvDocument);
        tvCamera = view.findViewById(R.id.tvCamera);
        tvVideo = view.findViewById(R.id.tvVideo);
        tvAudio = view.findViewById(R.id.tvAudio);
        etCrimeInfo = view.findViewById(R.id.etCrimeInfo);
        etInformerName = view.findViewById(R.id.etInformerName);
        etInformerPhone = view.findViewById(R.id.etInformerPhone);
        etInformerAddress = view.findViewById(R.id.etInformerAddress);
        etInformerNID = view.findViewById(R.id.etInformerNID);
        //requestMultiplePermissions();

        mediaPlayer = new MediaPlayer();
        etInformerEmail = view.findViewById(R.id.etInformerEmail);
        etInformerEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //informerEmailValue = etInformerEmail.getText().toString().trim();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                charSequence = s;
                informerEmailValue = etInformerEmail.getText().toString().trim();
                if (informerEmailValue.matches(emailPattern) && s.length() > 0) {
                    etInformerEmail.setBackgroundResource(R.drawable.cell_border);
                } else {
                    etInformerEmail.setBackgroundResource(R.drawable.cell_border_red);
                }
            }
        });
        // https:
//drive.google.com/file/d/1XXf3pUb3OC3xkcu4_Dje51xtCBzKDiaW/view?usp=sharing

        ibDocument = view.findViewById(R.id.ibDocument);
        ibCamera = view.findViewById(R.id.ibCamera);
        ibVideo = view.findViewById(R.id.ibVideo);
        ibAudio = view.findViewById(R.id.ibAudio);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnCancel = view.findViewById(R.id.btnCancel);
        videoView = view.findViewById(R.id.videoView);
        scrollViewAddInfo = view.findViewById(R.id.scrollViewAddInfo);
        fileProcessing = new FileProcessing(activity);

        llDocument = view.findViewById(R.id.llDocument);
        llCamera = view.findViewById(R.id.llCamera);
        llVideo = view.findViewById(R.id.llVideo);
        llAudio = view.findViewById(R.id.llAudio);

        tvDocumentShowHide = view.findViewById(R.id.tvDocumentShowHide);
        tvPicShowHide = view.findViewById(R.id.tvPicShowHide);
        tvVideoPlayStop = view.findViewById(R.id.tvVideoPlayStop);
        tvAudioPlayStop = view.findViewById(R.id.tvAudioPlayStop);

        tvAudioClear = view.findViewById(R.id.tvAudioClear);
        tvPicClear = view.findViewById(R.id.tvPicClear);
        tvDocumentClear = view.findViewById(R.id.tvDocumentClear);
        tvVideoClear = view.findViewById(R.id.tvVideoClear);


        pdfView = view.findViewById(R.id.pdfView);
        llPDFView = view.findViewById(R.id.llPDFView);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        ibDocument.setOnClickListener(this);
        ibCamera.setOnClickListener(this);
        ibVideo.setOnClickListener(this);
        ibAudio.setOnClickListener(this);


        tvDocumentShowHide.setOnClickListener(this);
        tvPicShowHide.setOnClickListener(this);
        tvVideoPlayStop.setOnClickListener(this);
        tvAudioPlayStop.setOnClickListener(this);

        tvDocumentClear.setOnClickListener(this);
        tvPicClear.setOnClickListener(this);
        tvVideoClear.setOnClickListener(this);
        tvAudioClear.setOnClickListener(this);


        spDistrict.setOnItemSelectedListener(this);
        spDistrictInformer.setOnItemSelectedListener(this);
        spDimoutInformer.setOnItemSelectedListener(this);
        spDimout.setOnItemSelectedListener(this);

        isDocumentShow = true;
        isPicsShow = true;
        isVideoPlay = true;
        isAudioPlay = true;
        //isVideoClearClicked = true;

        getDistrictJsonFile();
        getMohanogorJsonFile();
        getCountryJsonFile();


        pDialog = new ProgressDialog(activity);
        pDialog.setMessage(getString(R.string.progress_message));
        pDialog.setCancelable(false);

        districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listDistrict);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDistrict.setAdapter(districtAdapter);

        occurrence = new ArrayList<String>();
        occurrence.add(getString(R.string.mohanogor_district_bdout));
        occurrence.add(getString(R.string.mohanogor));
        occurrence.add(getString(R.string.district));
        occurrence.add(getString(R.string.bdout));

        occurrenceAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, occurrence);
        occurrenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDimout.setAdapter(occurrenceAdapter);
        spDimoutInformer.setAdapter(occurrenceAdapter);
        position = activity.passedPosition;
        categoryName = activity.passedCategoryName;
        crimeTitle();


        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spDimout:
                spDimout.setBackgroundResource(R.drawable.selector_dropdown);
                itemOccurrence = parent.getItemAtPosition(position).toString();
                if (itemOccurrence == getString(R.string.mohanogor)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listMohanogor);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
                    spDistrict.setVisibility(View.VISIBLE);
                    spThana.setVisibility(View.GONE);

                } else if (itemOccurrence == getString(R.string.district)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listDistrict);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
                    spDistrict.setVisibility(View.VISIBLE);
                    spThana.setVisibility(View.GONE);

                } else if (itemOccurrence == getString(R.string.bdout)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listCountry);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
                    spDistrict.setVisibility(View.VISIBLE);
                    spThana.setVisibility(View.GONE);
                } else if (itemOccurrence == getString(R.string.mohanogor_district_bdout)) {
                    spDistrict.setVisibility(View.GONE);
                    spThana.setVisibility(View.GONE);
                }

                break;
            case R.id.spDistrict:
                String itemDistrict = parent.getItemAtPosition(position).toString();
                if (itemOccurrence == getString(R.string.mohanogor)) {
                    List<String> mohanogorThana = getMohanogorThanaFromJson(itemDistrict);
                    mohanogorThanaAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, mohanogorThana);
                    mohanogorThanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spThana.setAdapter(mohanogorThanaAdapter);
                    spThana.setVisibility(View.VISIBLE);
                    spDistrict.setVisibility(View.VISIBLE);
                } else if (itemOccurrence == getString(R.string.district)) {
                    List<String> thana = getThanaFromJson(itemDistrict);
                    thanaAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, thana);
                    thanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spThana.setAdapter(thanaAdapter);
                    spThana.setVisibility(View.VISIBLE);
                    spDistrict.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.spDimoutInformer:
                itemOccurrenceInformer = parent.getItemAtPosition(position).toString();
                if (itemOccurrenceInformer == getString(R.string.mohanogor)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listMohanogor);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrictInformer.setAdapter(districtAdapter);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    spThanaInformer.setVisibility(View.GONE);
                } else if (itemOccurrenceInformer == getString(R.string.district)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listDistrict);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrictInformer.setAdapter(districtAdapter);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    spThanaInformer.setVisibility(View.GONE);
                } else if (itemOccurrenceInformer == getString(R.string.bdout)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listCountry);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrictInformer.setAdapter(districtAdapter);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    spThanaInformer.setVisibility(View.GONE);
                } else if (itemOccurrenceInformer == getString(R.string.mohanogor_district_bdout)) {
                    spDistrictInformer.setVisibility(View.GONE);
                    spThanaInformer.setVisibility(View.GONE);
                }
                break;

            case R.id.spDistrictInformer:
                String itemDistrictInformer = parent.getItemAtPosition(position).toString();
                if (itemOccurrenceInformer == getString(R.string.mohanogor)) {
                    List<String> mohanogorThana = getMohanogorThanaFromJson(itemDistrictInformer);
                    mohanogorThanaAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, mohanogorThana);
                    mohanogorThanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spThanaInformer.setAdapter(mohanogorThanaAdapter);
                    spThanaInformer.setVisibility(View.VISIBLE);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                } else if (itemOccurrenceInformer == getString(R.string.district)) {
                    List<String> thana = getThanaFromJson(itemDistrictInformer);
                    thanaAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, thana);
                    thanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spThanaInformer.setAdapter(thanaAdapter);
                    spThanaInformer.setVisibility(View.VISIBLE);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        scrollViewAddInfo.fullScroll(scrollViewAddInfo.FOCUS_DOWN);
        switch (view.getId()) {
            case R.id.ibDocument:
                enterStorage(getString(R.string.type_docs), PICK_FILE_REQUEST);
                stopVideoAudioPlayer();
                isDocumentClearClicked = false;
                break;
            case R.id.ibCamera:
                activity.imageSelectionDialog = new ImageSelectionDialog(activity, activity, itemPicFlag);
                DialogNavBarHide.navBarHide(activity, activity.imageSelectionDialog);
                stopVideoAudioPlayer();
                isImageClearClicked = false;
                break;
            case R.id.ibVideo:
                enterStorage(getString(R.string.type_video), PICK_VIDEO_REQUEST);
                stopVideoAudioPlayer();
                isVideoClearClicked = false;
                break;


            case R.id.ibAudio:
                enterStorage(getString(R.string.type_audio), PICK_AUDIO_REQUEST);
                stopVideoAudioPlayer();
                isAudioClearClicked = false;
                break;

            case R.id.tvDocumentShowHide:
                if (isDocumentShow) {
                    showPDF(pdfUrl);
                    llPDFView.setVisibility(View.VISIBLE);
                    tvDocumentShowHide.setText(getString(R.string.hide));
                    scrollViewAddInfo.requestDisallowInterceptTouchEvent(true);
                    isDocumentShow = false;
                } else {
                    tvDocumentShowHide.setText(getString(R.string.show));
                    llPDFView.setVisibility(View.GONE);
                    scrollViewAddInfo.requestDisallowInterceptTouchEvent(false);
                    isDocumentShow = true;
                }

                break;

            case R.id.tvPicShowHide:
                if (isPicsShow) {
                    ivCamera.setVisibility(View.VISIBLE);
                    tvPicShowHide.setText(getString(R.string.hide));
                    isPicsShow = false;
                } else {
                    ivCamera.setVisibility(View.GONE);
                    tvPicShowHide.setText(getString(R.string.show));
                    isPicsShow = true;
                }
                break;


            case R.id.tvVideoPlayStop:
                if (isVideoPlay) {
                    playVideo(uriVideo);
                    isVideoPlay = false;
                    videoView.setVisibility(View.VISIBLE);
                    tvVideoPlayStop.setText(getString(R.string.stop));
                } else {
                    if (videoView.isPlaying()) {
                        videoView.setVisibility(View.GONE);
                        tvVideoPlayStop.setText(getString(R.string.play));
                    }
                    isVideoPlay = true;
                }
                break;


            case R.id.tvAudioPlayStop:
                if (isAudioPlay) {
                    playAudio(uriAudio);
                    isAudioPlay = false;
                    tvAudioPlayStop.setText(getString(R.string.stop));

                } else {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        tvAudioPlayStop.setText(getString(R.string.play));
                    }
                    isAudioPlay = true;
                }

                break;


            case R.id.tvVideoClear:
                displayVideoFileName = "";
                displayVideoName = "";
                isVideoClearClicked = true;

                if (tvVideo != null || videoView.isPlaying()) {
                    tvVideo.setText("");
                    videoView.setVisibility(View.GONE);
                    llVideo.setVisibility(View.GONE);
                    tvVideoPlayStop.setText(getString(R.string.play));
                    isVideoPlay = true;
                }
                break;


            case R.id.tvAudioClear:
                displayAudioFileName = "";
                displayAudioName = "";
                isAudioClearClicked = true;

                if (tvAudio != null || mediaPlayer != null && mediaPlayer.isPlaying()) {
                    tvAudio.setText("");
                    mediaPlayer.stop();
                    llAudio.setVisibility(View.GONE);
                    tvAudioPlayStop.setText(getString(R.string.play));
                    isAudioPlay = true;
                }

                break;

            case R.id.tvPicClear:

                byteConvertedImage = "";
                imgPath = "";
                isImageClearClicked = false;

                if (tvCamera != null || ivCamera.isShown()) {
                    tvCamera.setText("");
                    ivCamera.setVisibility(View.GONE);
                    llCamera.setVisibility(View.GONE);
                    tvPicShowHide.setText(getString(R.string.show));
                    isPicsShow = true;
                }

                break;


            case R.id.tvDocumentClear:
                displayDocumentFileName = "";
                displayDocumentName = "";
                isDocumentClearClicked = true;

                if (tvDocument != null || llPDFView.isShown()) {
                    tvDocument.setText("");
                    llPDFView.setVisibility(View.GONE);
                    llDocument.setVisibility(View.GONE);
                    tvDocumentShowHide.setText(getString(R.string.show));
                    isDocumentShow = true;
                }

                break;


            case R.id.btnSubmit:

                if (InternetConnectionCheck.getConnectivityStatus(activity) != StaticAccess.TYPE_NOT_CONNECTED) {

                    if (etCrimeInfo.getText().toString().trim().length() > 0) {
                        if (spDimout.getSelectedItemPosition() != 0) {
                            if (etInformerEmail.getText().toString().trim().length() > 0) {
                                if ((informerEmailValue.matches(emailPattern) && charSequence.length() > 0)) {
                                    makeJSONObjectRequest();
                                    //new JsonSendRequest().execute();
                                    stopVideoAudioPlayer();
                                    spDimout.setBackgroundResource(R.drawable.selector_dropdown);
                                } else {
                                    Toast.makeText(activity, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                makeJSONObjectRequest();

                                //new JsonSendRequest().execute();
                                stopVideoAudioPlayer();
                                spDimout.setBackgroundResource(R.drawable.selector_dropdown);
                            }
                        } else {
                            Toast.makeText(activity, getString(R.string.select_occurence_place), Toast.LENGTH_SHORT).show();
                            spDimout.setBackgroundResource(R.drawable.selector_dropdown_red);

                        }
                        etCrimeInfo.setBackgroundResource(R.drawable.cell_border);
                    } else {
                        etCrimeInfo.setBackgroundResource(R.drawable.cell_border_red);
                        Toast.makeText(activity, getResources().getString(R.string.inform_terrorism), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, getString(R.string.internet_check), Toast.LENGTH_SHORT).show();
                }










               /* if (etCrimeInfo.getText().toString().trim().length() > 0) {
                    if (spDimout.getSelectedItemPosition() != 0) {
                        makeJSONObjectRequest();

                        stopVideoAudioPlayer();
                        spDimout.setBackgroundResource(R.drawable.selector_dropdown);
                    } else {
                        Toast.makeText(activity, getString(R.string.select_occurence_place), Toast.LENGTH_SHORT).show();
                        spDimout.setBackgroundResource(R.drawable.selector_dropdown_red);

                    }
                    etCrimeInfo.setBackgroundResource(R.drawable.cell_border);
                } else {
                    etCrimeInfo.setBackgroundResource(R.drawable.cell_border_red);
                    Toast.makeText(activity, getResources().getString(R.string.inform_terrorism), Toast.LENGTH_SHORT).show();
                }*/











           /*     if (etCrimeInfo.getText().length() > 0) {
                    crime.setCrimCategory(categoryName);
                    crime.setCrimPosition(position);
                    crime.setCrimeInfo(etCrimeInfo.getText().toString());
                    crime.setInformerName(etInformerName.getText().toString());
                    crime.setInformerPhone(etInformerPhone.getText().toString());
                    crime.setInformerAddress(etInformerAddress.getText().toString());

                    if (activity.documentName != null) {
                        crime.setInfoDocument(activity.documentName.toString());
                    } else {
                        crime.setInfoDocument("");
                    }

                    //byte[] imageBytePath =  checkGettingImage(imgPath).getBytes();

                    if (checkGettingImage(byteConvertedImage) != null) {
                        crime.setInfoPicture(byteConvertedImage);
                    } else {
                        crime.setInfoPicture("");
                    }

                    if (activity.videoName != null) {
                        crime.setInfoVideo(activity.videoName.toString());
                    } else {
                        crime.setInfoVideo("");
                    }

                    if (activity.audioName != null) {
                        crime.setInfoAudio(activity.audioName.toString());
                    } else {
                        crime.setInfoAudio("");
                    }


                    if (spDimout.getSelectedItem().toString() != null) {
                        crime.setOccurrence(spDimout.getSelectedItem().toString());
                    } else {
                        crime.setOccurrence(" ");
                    }

                    if (spDimoutInformer.getSelectedItem().toString() != null) {
                        crime.setOccurrenceInformer(spDimoutInformer.getSelectedItem().toString());
                    } else {
                        crime.setOccurrenceInformer(" ");
                    }

                    if (spDistrict.getSelectedItem().toString() != null) {
                        crime.setDivision(spDistrict.getSelectedItem().toString());
                    } else {
                        crime.setDivision(" ");
                    }


                    if (spThana.getSelectedItem().toString() != null) {
                        crime.setDistrict(spThana.getSelectedItem().toString());
                    } else {
                        crime.setDistrict(" ");
                    }
                    if (spThanaInformer.getSelectedItem().toString() != null) {
                        crime.setDistrictInformer(spThanaInformer.getSelectedItem().toString());
                    } else {
                        crime.setDistrictInformer(" ");
                    }


                    crime.setCreatedAt(new Date());
                    imageProcessing.setImageWith_loader(ivCamera, byteConvertedImage);
                    //imageProcessing.setImageWith_loader(ivCamera, imgPath);
                    databaseManager.insertCrime(crime);

                    Gson gson = new Gson();
                    String json = gson.toJson(crime); //convert
                    System.out.println(json);

                    Toast.makeText(activity, getResources().getString(R.string.successful_message), Toast.LENGTH_SHORT).show();
                    backToPrevious();
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.inform_terrorism), Toast.LENGTH_SHORT).show();
                }
                stopVideoAudioPlayer();
               */

                break;
            case R.id.btnCancel:
                backToPrevious();
                stopVideoAudioPlayer();
                break;
        }
    }


    @SuppressLint("SetTextI18n")
    public String resultActivityForDocument(int requestCode, int resultCode, Intent data, TextView textView, LinearLayout linearLayout) {

        if (resultCode == RESULT_OK) {
            Cursor cursor = null;
            linearLayout.setVisibility(View.VISIBLE);
            if (requestCode == PICK_FILE_REQUEST && textView == tvDocument) {
                uriDataDocument = data.getData();

                String scheme = uriDataDocument.getScheme();
                System.out.println("Scheme type " + scheme);
                if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                    try {
                        InputStream fileInputStream = activity.getApplicationContext().getContentResolver().openInputStream(uriDataDocument);
                        documentFileSize = ((fileInputStream.available()) / 1024) / 1024;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(activity,  documentFileSize +"MB", Toast.LENGTH_LONG).show();
                }


                String uriStringData = uriDataDocument.toString();

                try {
                    cursor = activity.getContentResolver().query(uriDataDocument, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayDocumentFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        try {
                            //displayDocumentName = Base64.encodeToString(getBytes(uriStringData), Base64.NO_WRAP);


                            if (documentFileSize >5) {
                                CustomToast.t(activity, getResources().getString(R.string.not_supported_document));
                                llDocument.setVisibility(View.GONE);
                            } else {
                                InputStream inputStream = activity.getContentResolver().openInputStream(uriDataDocument);
                                bytesDocument = getConvertedData(inputStream);
                                displayDocumentName = Base64.encodeToString(bytesDocument, Base64.DEFAULT);
                            }


                            /*InputStream inputStream = activity.getContentResolver().openInputStream(uriData);
                            byte[] bytes = getConvertedData(inputStream);
                            displayDocumentName = Base64.encodeToString(bytes, Base64.DEFAULT);*/

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        textView.setVisibility(View.VISIBLE);
                        textView.setText(getString(R.string.document) + ": " + displayDocumentFileName);
                        pdfUrl = data.getData();

                    }
                } finally {
                    cursor.close();
                }

            }
        }
        return displayDocumentName;
    }

    public String resultActivityForVideo(int requestCode, int resultCode, Intent data, TextView textView, LinearLayout linearLayout) {

        if (resultCode == RESULT_OK) {
            Cursor cursor = null;
            linearLayout.setVisibility(View.VISIBLE);
            if (requestCode == PICK_VIDEO_REQUEST && textView == tvVideo) {
                uriDataVideo = data.getData();


                String scheme = uriDataVideo.getScheme();
                System.out.println("Scheme type " + scheme);
                if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                    try {
                        InputStream fileInputStream = activity.getApplicationContext().getContentResolver().openInputStream(uriDataVideo);
                        videoFileSize = ((fileInputStream.available()) / 1024) / 1024;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(activity,  videoFileSize +"MB", Toast.LENGTH_LONG).show();

                }


                String uriStringData = uriDataVideo.toString();
                try {
                    cursor = activity.getContentResolver().query(uriDataVideo, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayVideoFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        try {
                            //displayVideoName = Base64.encodeToString(getBytes(uriStringData), Base64.NO_WRAP);

                            if (videoFileSize > 100) {
                                CustomToast.t(activity, getResources().getString(R.string.not_supported_video));
                                llVideo.setVisibility(View.GONE);
                            } else {
                                InputStream inputStream = activity.getContentResolver().openInputStream(uriDataVideo);
                                bytesVideo = getConvertedData(inputStream);
                                displayVideoName = Base64.encodeToString(bytesVideo, Base64.DEFAULT);
                            }

                            /*InputStream inputStream = activity.getContentResolver().openInputStream(uriData);
                            byte[] bytes = getConvertedData(inputStream);
                            displayVideoName = Base64.encodeToString(bytes, Base64.DEFAULT);*/

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(getString(R.string.video) + ": " + displayVideoFileName);
                        uriVideo = data.getData();
                    }
                } finally {
                    cursor.close();
                }

            }

        }
        return displayVideoName;
    }


    public String resultActivityForAudio(int requestCode, int resultCode, Intent data, TextView textView, LinearLayout linearLayout) {

        if (resultCode == RESULT_OK) {
            Cursor cursor = null;
            linearLayout.setVisibility(View.VISIBLE);
            if (requestCode == PICK_AUDIO_REQUEST && textView == tvAudio) {
                uriDataAudio = data.getData();

                String scheme = uriDataAudio.getScheme();
                System.out.println("Scheme type " + scheme);
                if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                    try {
                        InputStream fileInputStream = activity.getApplicationContext().getContentResolver().openInputStream(uriDataAudio);
                        audioFileSize = ((fileInputStream.available()) / 1024) / 1024;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(activity,  audioFileSize +"MB", Toast.LENGTH_LONG).show();
                }
                //String uriStringData = uriData.toString();
                try {
                    cursor = activity.getContentResolver().query(uriDataAudio, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayAudioFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        try {
                            //displayAudioName = Base64.encodeToString(getBytes(uriStringData), Base64.NO_WRAP);
                            if (audioFileSize > 15) {
                                CustomToast.t(activity, getResources().getString(R.string.not_supported_audio));
                                llAudio.setVisibility(View.GONE);
                            } else {
                                InputStream inputStream = activity.getContentResolver().openInputStream(uriDataAudio);
                                bytesAudio = getConvertedData(inputStream);
                                displayAudioName = Base64.encodeToString(bytesAudio, Base64.DEFAULT);
                            }
                           /* InputStream inputStream = activity.getContentResolver().openInputStream(uriData);
                            byte[] bytes = getConvertedData(inputStream);
                            displayAudioName = Base64.encodeToString(bytes, Base64.DEFAULT);*/


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(getString(R.string.audio) + ": " + displayAudioFileName);
                        uriAudio = data.getData();
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        return displayAudioName;
    }


    public byte[] getConvertedData(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public static byte[] getBytes(Object obj) throws java.io.IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
        bos.close();
        byte[] data = bos.toByteArray();
        return data;
    }


    public void playVideo(Uri uri) {
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!isVideoPlay) {
                    videoView.setVisibility(View.GONE);
                    tvVideoPlayStop.setText(getString(R.string.play));
                    isVideoPlay = true;
                }
            }
        });
        videoView.start();
    }

    public void stopVideoAudioPlayer() {
        if (videoView.isPlaying()) {
            videoView.stopPlayback();
            videoView.setVisibility(View.GONE);
            tvVideoPlayStop.setText(getString(R.string.play));
            isVideoPlay = true;
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            tvAudioPlayStop.setText(getString(R.string.play));
            isAudioPlay = true;
        }

        if (llPDFView.isShown()) {
            llPDFView.setVisibility(View.GONE);
            tvDocumentShowHide.setText(getString(R.string.show));
            isDocumentShow = true;
        }
        if (ivCamera.isShown()) {
            ivCamera.setVisibility(View.GONE);
            tvPicShowHide.setText(getString(R.string.show));
            isPicsShow = true;
        }

    }

    public void playAudio(Uri uri) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(activity, uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (!isAudioPlay) {
                        tvAudioPlayStop.setText(getString(R.string.play));
                        isAudioPlay = true;
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void showPDF(Uri pdfURL) {
        pdfView.fromUri(pdfURL)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        pageNumber = page;
                    }
                })
                .enableAnnotationRendering(true)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                    }
                })
                .scrollHandle(new DefaultScrollHandle(activity))
                .load();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setImagePro(Bitmap bitmap, TextView textView) {
        //scale bitmap
        if (bitmap != null) {
            bitmapImage = bitmap;
            byteConvertedImage = Base64.encodeToString(imgProc.getBytesFromBitmap(bitmap), Base64.NO_WRAP);
            imgPath = imgProc.imageSave(bitmapImage);
            textView.setText(getString(R.string.picture) + ": " + imgPath);
            imgProc.setImageWith_loader(ivCamera, imgPath);
            //bitmapImage.recycle();


        }
    }


    public void enterStorage(String type, int FLAG) {
        Intent intent = new Intent();
        intent.setType(type);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(Intent.createChooser(intent, "Choose File to Upload"), FLAG);
    }

    String checkGettingImage(String imgPath) {
        String imgFilePath;
        if (imgPath.length() > 0) {
            imgFilePath = imgPath;
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_atu);
//            imgProc.imageSave(bitmap);
            imgFilePath = imageProcessing.imageSave(bitmap);
        }
        return imgFilePath;
    }

    private void crimeTitle() {
        if (position == 0) {
            tvCrimeTitle.setText(R.string.a);
        } else if (position == 1) {
            tvCrimeTitle.setText(R.string.b);
        } else if (position == 2) {
            tvCrimeTitle.setText(R.string.c);
        } else if (position == 3) {
            tvCrimeTitle.setText(R.string.d);
        }
    }

    public void backToPrevious() {
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
        activity.finish();
    }


    public String loadJSONFromAsset(String jsonFile) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public List<String> getThanaFromJson(String districtName) {
        try {
            List<String> thanaByDistrict = new ArrayList<String>();
            for (DistrictMain list : listDistrictMain) {
                if (list.getDistrict() == districtName) {
                    List<String> listthanaCount = list.getThanaList();
                    for (int i = 0; i < listthanaCount.size(); i++) {
                        thanaByDistrict.add(listthanaCount.get(i));
                    }
                }
            }
            return thanaByDistrict;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getMohanogorThanaFromJson(String mohanogorName) {
        try {
            List<String> thanaByMohanogor = new ArrayList<String>();
            for (MohanogorMain list : mohanogorMains) {
                if (list.getMohanogor() == mohanogorName) {
                    List<String> listthanaCount = list.getThanaList();
                    for (int i = 0; i < listthanaCount.size(); i++) {
                        thanaByMohanogor.add(listthanaCount.get(i));
                    }
                }
            }
            return thanaByMohanogor;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void getDistrictJsonFile() {
        try {
            Gson gson = new Gson();
            DistrictList ds = gson.fromJson(loadJSONFromAsset(jsonDistrict), DistrictList.class);
            listDistrictMain = ds.getDistrictList();
            listDistrict = new ArrayList<String>();


            for (DistrictMain list : listDistrictMain) {
                listDistrict.add(list.getDistrict());
                List<String> listthanaCount = list.getThanaList();

                listDistrictThana = new ArrayList<String>();
                for (int i = 0; i < listthanaCount.size(); i++) {
                    listDistrictThana.add(listthanaCount.get(i));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMohanogorJsonFile() {
        try {
            Gson gson = new Gson();
            Mohanogor mohanogor = gson.fromJson(loadJSONFromAsset(jsonMohanogor), Mohanogor.class);
            mohanogorMains = mohanogor.getMohanogorMain();
            listMohanogor = new ArrayList<String>();

            for (MohanogorMain list : mohanogorMains) {
                listMohanogor.add(list.getMohanogor());

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCountryJsonFile() {
        try {
            listCountry = new ArrayList<String>();
            Gson gson = new Gson();
            CountryList countryList = gson.fromJson(loadJSONFromAsset(jsonCountry), CountryList.class);
            List<String> listCount = countryList.getCountryList();
            for (int i = 0; i < listCount.size(); i++) {
                listCountry.add(listCount.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //StringRequest stringRequest;
    VolleyMultipartRequest stringRequest;

    /// making json request
    private void makeJSONObjectRequest() {

        showpDialog();
        stringRequest = new VolleyMultipartRequest(Request.Method.POST, ROOT_URL_ATU,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        hidepDialog();
                        Toast.makeText(activity, getResources().getString(R.string.successful_message), Toast.LENGTH_SHORT).show();
                        backToPrevious();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                       /* NetworkResponse response = error.networkResponse;
                        String errorMsg = "";
                        if (response != null && response.data != null) {
                            String errorString = new String(response.data);
                            Log.i("log error", errorString);
                        }
                        Toast.makeText(activity.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(activity, getResources().getString(R.string.failed_message), Toast.LENGTH_SHORT).show();
*/

                        String message = null;
                        if (error instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";

                        } else if (error instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (error instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        //Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                        CustomToast.t(activity, message);

                        hidepDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String date = String.valueOf(new Date());
                String tokenValue = getString(R.string.token);
                String category = categoryName;
                String positionvalue = String.valueOf(position);
                String crimInfoValue = etCrimeInfo.getText().toString();
                String informerAddressValue = etInformerAddress.getText().toString();
                String informerNIDValue = etInformerNID.getText().toString();
                String informerNameValue = etInformerName.getText().toString();
                String informerPhoneValue = etInformerPhone.getText().toString();
                audioFile = activity.audioName;
                documentFile = activity.documentName;
                imagefile = byteConvertedImage;
                videofile = activity.videoName;

                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "multipart/form-data");
                params.put(TAG_CRIME_TYPE, toBase64(category));
                params.put(TAG_APP_AUTH_TOKEN, toBase64(tokenValue));

                if (etCrimeInfo.getText().toString().trim().length() > 0) {
                    params.put(TAG_CRIME_INFO, toBase64(crimInfoValue));
                }

                if (etInformerAddress.getText().toString().trim().length() > 0) {
                    params.put(TAG_INFORMER_ADDRESS, toBase64(informerAddressValue));
                } else {
                    params.put(TAG_INFORMER_ADDRESS, "");
                }


                if (etInformerEmail.getText().toString().trim().length() > 0) {
                    params.put(TAG_INFORMER_EMAIL, toBase64(informerEmailValue));
                } else {
                    params.put(TAG_INFORMER_EMAIL, "");
                }


                if (etInformerNID.getText().toString().trim().length() > 0) {
                    params.put(TAG_INFORMER_NID, toBase64(informerNIDValue));
                } else {
                    params.put(TAG_INFORMER_NID, "");
                }

                if (etInformerName.getText().toString().trim().length() > 0) {
                    params.put(TAG_INFORMER_NAME, toBase64(informerNameValue));
                } else {
                    params.put(TAG_INFORMER_NAME, "");
                }

                if (etInformerPhone.getText().toString().trim().length() > 0) {
                    params.put(TAG_INFORMER_PHONE, toBase64(informerPhoneValue));
                } else {
                    params.put(TAG_INFORMER_PHONE, "");
                }


                //toBase64(spThana.getSelectedItem().toString());

                if (spThana.getSelectedItem() != null) {
                    //params.put(TAG_THANA, spThana.getSelectedItem().toString());
                    params.put(TAG_THANA, toBase64(spThana.getSelectedItem().toString()));
                } else {
                    params.put(TAG_THANA, "");
                }


                if (spThanaInformer.getSelectedItem() != null) {
                    params.put(TAG_INFORMER_THANA, toBase64(spThanaInformer.getSelectedItem().toString()));
                } else {
                    params.put(TAG_INFORMER_THANA, "");
                }

                if (spDistrict.getSelectedItem() != null) {
                    params.put(TAG_DISTRICT_OR_METROPOLITAN, toBase64(spDistrict.getSelectedItem().toString()));
                } else {
                    params.put(TAG_DISTRICT_OR_METROPOLITAN, "");
                }


                if (spDistrictInformer.getSelectedItem() != null) {
                    params.put(TAG_INFORMER_DISTRICT_OR_METROPOLITAN, toBase64(spDistrictInformer.getSelectedItem().toString()));
                } else {
                    params.put(TAG_INFORMER_DISTRICT_OR_METROPOLITAN, "");
                }


                if (spDimout.getSelectedItem() != null) {
                    params.put(TAG_DIVISION_OR_COUNTRY, toBase64(spDimout.getSelectedItem().toString()));
                } else {
                    params.put(TAG_DIVISION_OR_COUNTRY, "");
                }

                //Rokan
                if (spDimoutInformer.getSelectedItem() != null) {
                    params.put(TAG_INFORMER_DIVISION_OR_COUNTRY, toBase64(spDimoutInformer.getSelectedItem().toString()));
                } else {
                    params.put(TAG_INFORMER_DIVISION_OR_COUNTRY, "");
                }


                if (audioFile != null && !TextUtils.isEmpty(audioFile)) {
                    params.put(TAG_INFO_AUDIO_NAME, toBase64(displayAudioFileName));
                } else {
                    params.put(TAG_INFO_AUDIO_NAME, "");
                }


                if (documentFile != null && !TextUtils.isEmpty(documentFile)) {
                    params.put(TAG_INFO_DOCUMENT_NAME, toBase64(displayDocumentFileName));
                } else {
                    params.put(TAG_INFO_DOCUMENT_NAME, "");
                }


                if (imagefile != null && !TextUtils.isEmpty(imagefile)) {
                    params.put(TAG_INFO_PICTURE_NAME, toBase64(imgPath));
                } else {
                    params.put(TAG_INFO_PICTURE_NAME, "");
                }


                if (videofile != null && !TextUtils.isEmpty(videofile)) {
                    params.put(TAG_INFO_VIDEO_NAME, toBase64(displayVideoFileName));
                } else {
                    params.put(TAG_INFO_VIDEO_NAME, "");
                }


                Gson gson = new Gson();
                String json = gson.toJson(params); //convert
                System.out.println(json);

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                Uri uriEmpty = Uri.parse("");
                String abc = "";
                byte[] emptyBytes = abc.getBytes();


/*

                //params.put(TAG_INFO_PICTURE, new DataPart(imgPath, getImageDataFromDrawable(bitmapImage)));
                params.put(TAG_INFO_PICTURE, new DataPart(imgPath, imgProc.getBytesFromBitmap(bitmapImage)));
                //params.put(TAG_INFO_PICTURE, new DataPart(imgPath, getFileDataFromDrawable(getActivity(), activity.uriDataImage)));
                params.put(TAG_INFO_VIDEO, new DataPart(displayVideoFileName, getFileDataFromDrawable(getActivity(), uriDataVideo)));
                params.put(TAG_INFO_AUDIO, new DataPart(displayAudioFileName, getFileDataFromDrawable(getActivity(), uriDataAudio)));
                params.put(TAG_INFO_DOCUMENT, new DataPart(displayDocumentFileName, getFileDataFromDrawable(getActivity(), uriDataDocument)));

*/

                if (imagefile != null && !TextUtils.isEmpty(imagefile)) {
                    if (isImageClearClicked) {
                        params.put(TAG_INFO_PICTURE, new DataPart("", emptyBytes));
                        isImageClearClicked = false;
                    } else {
                        params.put(TAG_INFO_PICTURE, new DataPart(imgPath, imgProc.getBytesFromBitmap(bitmapImage)));
                    }
                } else {
                    params.put(TAG_INFO_PICTURE, new DataPart("", emptyBytes));
                }


                if (videofile != null && !TextUtils.isEmpty(videofile)) {
                    if (isVideoClearClicked) {
                        params.put(TAG_INFO_VIDEO, new DataPart("", emptyBytes));
                        isVideoClearClicked = false;
                    } else {
                        params.put(TAG_INFO_VIDEO, new DataPart(displayVideoFileName, getFileDataFromDrawable(getActivity(), uriDataVideo)));
                    }
                } else {
                    params.put(TAG_INFO_VIDEO, new DataPart("", emptyBytes));
                }

                if (audioFile != null && !TextUtils.isEmpty(audioFile)) {
                    if (isAudioClearClicked) {
                        params.put(TAG_INFO_AUDIO, new DataPart("", emptyBytes));
                        isAudioClearClicked = false;
                    } else {
                        params.put(TAG_INFO_AUDIO, new DataPart(displayAudioFileName, getFileDataFromDrawable(getActivity(), uriDataAudio)));
                    }
                } else {
                    params.put(TAG_INFO_AUDIO, new DataPart("", emptyBytes));
                }


                if (documentFile != null && !TextUtils.isEmpty(documentFile)) {
                    if (isDocumentClearClicked) {
                        params.put(TAG_INFO_DOCUMENT, new DataPart("", emptyBytes));
                        isDocumentClearClicked = false;
                    } else {
                        params.put(TAG_INFO_DOCUMENT, new DataPart(displayDocumentFileName, getFileDataFromDrawable(getActivity(), uriDataDocument)));
                    }
                } else {
                    params.put(TAG_INFO_DOCUMENT, new DataPart("", emptyBytes));
                }


                Gson gson = new Gson();
                String json = gson.toJson(params); //convert
                System.out.println(json);

                return params;
            }

        };

        //new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                -1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }



    public static String toBase64(String value){
        if (value == null)
            value = "";
        return Base64.encodeToString(value.trim().getBytes(), android.util.Base64.DEFAULT);
    }




  /*  public byte[] getImageDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }*/

    public static byte[] getFileDataFromDrawable(Context context, Uri uri) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream iStream = context.getContentResolver().openInputStream(uri);
            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            if (iStream != null) {
                while ((len = iStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }


    private void requestMultiplePermissions() {
        Dexter.withActivity(activity)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(activity, "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(activity, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

}
