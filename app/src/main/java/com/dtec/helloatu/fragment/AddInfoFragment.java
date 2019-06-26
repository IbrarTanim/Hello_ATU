package com.dtec.helloatu.fragment;

import com.dtec.helloatu.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.util.Date;

import com.dtec.helloatu.activities.FragmentBaseActivity;
import com.dtec.helloatu.activities.MainActivity;
import com.dtec.helloatu.dao.Crime;
import com.dtec.helloatu.dialogue.DialogNavBarHide;
import com.dtec.helloatu.dialogue.ImageSelectionDialog;
import com.dtec.helloatu.manager.DatabaseManager;
import com.dtec.helloatu.utilities.ImageProcessing;
import com.dtec.helloatu.utilities.MarshMallowPermission;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.motion.widget.MotionScene.TAG;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_AUDIO_REQUEST;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_FILE_REQUEST;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_VIDEO_REQUEST;

public class AddInfoFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, OnPageChangeListener, OnLoadCompleteListener, OnPageScrollListener {

    FragmentBaseActivity activity;
    public MarshMallowPermission marshMallowPermission;
    ImageProcessing imageProcessing;
    public Spinner spThana;
    public Spinner spOccurrence;
    public Spinner spOccurrenceInformer;
    public Spinner spDivision;
    public Spinner spDivisionInformer;
    public Spinner spDistrict;
    public Spinner spDistrictInformer;
    Button btnSubmit, btnCancel;
    EditText etCrimeInfo, etInformerName, etInformerPhone, etInformerAddress;
    PDFView pdfView;
    LinearLayout llPDFView;
    public LinearLayout llDocument, llCamera, llVideo, llAudio;
    public TextView tvDocument, tvCamera, tvVideo, tvAudio, tvCrimeTitle;
    TextView tvDocumentShowHide, tvPicShowHide, tvVideoPlayStop, tvAudioPlayStop;

    public ImageView ivCamera;
    String imgPath = "";
    String videoCheck;
    Crime crime;
    ImageProcessing imgProc;
    String displayName;
    Uri pdfUrl;
    public int itemPicFlag = 0;
    int position = -1;
    Integer pageNumber = 0;

    Uri uriVideo;
    Uri uriAudio;

    boolean isDocumentShow = false;
    boolean isPicsShow = false;
    boolean isVideoPlay = false;
    boolean isAudioPlay = false;

    VideoView videoView;
    public MediaPlayer mediaPlayer;
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
        spDivision = view.findViewById(R.id.spDivision);
        spDivisionInformer = view.findViewById(R.id.spDivisionInformer);
        spDistrictInformer = view.findViewById(R.id.spDistrictInformer);
        spDistrict = view.findViewById(R.id.spDistrict);
        spThana = view.findViewById(R.id.spThana);
        spOccurrenceInformer = view.findViewById(R.id.spOccurrenceInformer);
        spOccurrence = view.findViewById(R.id.spOccurrence);
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
        ibDocument = view.findViewById(R.id.ibDocument);
        ibCamera = view.findViewById(R.id.ibCamera);
        ibVideo = view.findViewById(R.id.ibVideo);
        ibAudio = view.findViewById(R.id.ibAudio);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnCancel = view.findViewById(R.id.btnCancel);
        videoView = view.findViewById(R.id.videoView);
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

      /*  mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!isAudioPlay) {
                    tvAudioPlayStop.setText(getString(R.string.play));
                    isAudioPlay = true;
                }

            }
        });*/

        llDocument = view.findViewById(R.id.llDocument);
        llCamera = view.findViewById(R.id.llCamera);
        llVideo = view.findViewById(R.id.llVideo);
        llAudio = view.findViewById(R.id.llAudio);

        tvDocumentShowHide = view.findViewById(R.id.tvDocumentShowHide);
        tvPicShowHide = view.findViewById(R.id.tvPicShowHide);
        tvVideoPlayStop = view.findViewById(R.id.tvVideoPlayStop);
        tvAudioPlayStop = view.findViewById(R.id.tvAudioPlayStop);
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

        spDivision.setOnItemSelectedListener(this);
        spDivisionInformer.setOnItemSelectedListener(this);
        spDistrict.setOnItemSelectedListener(this);
        spDistrictInformer.setOnItemSelectedListener(this);
        spThana.setOnItemSelectedListener(this);
        spOccurrenceInformer.setOnItemSelectedListener(this);
        spOccurrence.setOnItemSelectedListener(this);

        isDocumentShow = true;
        isPicsShow = true;
        isVideoPlay = true;
        isAudioPlay = true;

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
        divisionAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, division);
        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDivision.setAdapter(divisionAdapter);

        occurrence = new ArrayList<String>();
        occurrence.add(getString(R.string.mohanogor_district_bdout));
        occurrence.add(getString(R.string.mohanogor));
        occurrence.add(getString(R.string.district));
        occurrence.add(getString(R.string.bdout));

        occurrenceAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, occurrence);
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

        ArrayAdapter<String> thanaAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, thanaDhaka);
        thanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spThana.setAdapter(thanaAdapter);

        position = activity.passedPosition;
        crimeTitle();
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spOccurrence:
                String itemOccurrence = parent.getItemAtPosition(position).toString();
                if (itemOccurrence == getString(R.string.mohanogor)) {
                    divisionAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, division);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivision.setAdapter(divisionAdapter);
                    spDivision.setVisibility(View.VISIBLE);
                    spDistrict.setVisibility(View.GONE);
                    spThana.setVisibility(View.GONE);
                } else if (itemOccurrence == getString(R.string.district)) {
                    divisionAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, dstDhaka);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivision.setAdapter(divisionAdapter);
                    spDivision.setVisibility(View.VISIBLE);
                    spDistrict.setVisibility(View.GONE);
                    spThana.setVisibility(View.GONE);
                } else if (itemOccurrence == getString(R.string.bdout)) {
                    divisionAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, thanaDhaka);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivision.setAdapter(divisionAdapter);
                    spDivision.setVisibility(View.VISIBLE);
                    spDistrict.setVisibility(View.GONE);
                    spThana.setVisibility(View.GONE);
                }
                break;
            case R.id.spDivision:
                String itemDivision = parent.getItemAtPosition(position).toString();
                if (itemDivision == getString(R.string.div_dhaka)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, dstDhaka);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
                    spDistrict.setVisibility(View.VISIBLE);
                    spDivision.setVisibility(View.VISIBLE);
                    spThana.setVisibility(View.GONE);
                } else if (itemDivision == getString(R.string.div_chittagong)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, dstChittagong);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
                    spDistrict.setVisibility(View.VISIBLE);
                    spDivision.setVisibility(View.VISIBLE);
                    spThana.setVisibility(View.GONE);
                } else if (itemDivision == getString(R.string.div_sylhet)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, dstSylhet);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
                    spDistrict.setVisibility(View.VISIBLE);
                    spDivision.setVisibility(View.VISIBLE);
                    spThana.setVisibility(View.GONE);
                }
                break;
            case R.id.spDistrict:
                String itemDistrict = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spThana:
                String itemThana = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spOccurrenceInformer:
                String itemOccurrenceInformer = parent.getItemAtPosition(position).toString();
                if (itemOccurrenceInformer == getString(R.string.mohanogor)) {
                    divisionAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, division);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivisionInformer.setAdapter(divisionAdapter);
                    spDivisionInformer.setVisibility(View.VISIBLE);
                    spDistrictInformer.setVisibility(View.GONE);
                    // spThanaInformer.setVisibility(View.GONE);
                } else if (itemOccurrenceInformer == getString(R.string.district)) {
                    divisionAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, dstDhaka);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivisionInformer.setAdapter(divisionAdapter);
                    spDivisionInformer.setVisibility(View.VISIBLE);
                    spDistrictInformer.setVisibility(View.GONE);
                    //spThana.setVisibility(View.GONE);
                } else if (itemOccurrenceInformer == getString(R.string.bdout)) {
                    divisionAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, thanaDhaka);
                    divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDivisionInformer.setAdapter(divisionAdapter);
                    spDivisionInformer.setVisibility(View.VISIBLE);
                    spDistrictInformer.setVisibility(View.GONE);
                    //spThana.setVisibility(View.GONE);
                }
                break;

            case R.id.spDivisionInformer:
                String itemDivisionInformer = parent.getItemAtPosition(position).toString();
                if (itemDivisionInformer == getString(R.string.div_dhaka)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, dstDhaka);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrictInformer.setAdapter(districtAdapter);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    spDivisionInformer.setVisibility(View.VISIBLE);
                    //spThana.setVisibility(View.GONE);
                } else if (itemDivisionInformer == getString(R.string.div_chittagong)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, dstChittagong);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrictInformer.setAdapter(districtAdapter);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    spDivisionInformer.setVisibility(View.VISIBLE);
                    //spThana.setVisibility(View.GONE);
                } else if (itemDivisionInformer == getString(R.string.div_sylhet)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, dstSylhet);
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


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ibDocument:
                enterStorage(getString(R.string.type_docs), PICK_FILE_REQUEST);
                stopVideoAudioPlayer();
                break;
            case R.id.ibCamera:
                activity.imageSelectionDialog = new ImageSelectionDialog(activity, activity, itemPicFlag);
                DialogNavBarHide.navBarHide(activity, activity.imageSelectionDialog);
                stopVideoAudioPlayer();
                break;
            case R.id.ibVideo:
                enterStorage(getString(R.string.type_video), PICK_VIDEO_REQUEST);
                stopVideoAudioPlayer();
                break;

            case R.id.ibAudio:
                enterStorage(getString(R.string.type_audio), PICK_AUDIO_REQUEST);
                stopVideoAudioPlayer();
                break;

            case R.id.tvDocumentShowHide:
                if (isDocumentShow) {
                    showPDF(pdfUrl);
                    llPDFView.setVisibility(View.VISIBLE);
                    tvDocumentShowHide.setText(getString(R.string.hide));
                    isDocumentShow = false;
                } else {
                    tvDocumentShowHide.setText(getString(R.string.show));
                    llPDFView.setVisibility(View.GONE);
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

            case R.id.btnSubmit:
                if (etCrimeInfo.getText().length() > 0) {
                    crime.setCrimPosition(position);
                    crime.setCrimeInfo(etCrimeInfo.getText().toString());
                    crime.setOccurrence(spOccurrence.getSelectedItemPosition());
                    crime.setInformerName(etInformerName.getText().toString());
                    crime.setInformerPhone(etInformerPhone.getText().toString());
                    crime.setInformerAddress(etInformerAddress.getText().toString());

                    if (activity.documentName != null) {
                        crime.setInfoDocument(activity.documentName);
                    } else {
                        crime.setInfoDocument("");
                    }

                    if (checkGettingImage(imgPath) != null) {
                        crime.setInfoPicture(checkGettingImage(imgPath));
                    } else {
                        crime.setInfoPicture("");
                    }

                    if (activity.videoName != null) {
                        crime.setInfoVideo(activity.videoName);
                    } else {
                        crime.setInfoVideo("");
                    }

                    if (activity.audioName != null) {
                        crime.setInfoAudio(activity.audioName);
                    } else {
                        crime.setInfoAudio("");
                    }

                    crime.setOccurrence(spOccurrence.getSelectedItemPosition());
                    crime.setOccurrenceInformer(spOccurrenceInformer.getSelectedItemPosition());
                    crime.setDivision(spDivision.getSelectedItemPosition());
                    crime.setDivisionInformer(spDivisionInformer.getSelectedItemPosition());
                    crime.setDistrict(spDistrict.getSelectedItemPosition());
                    crime.setDistrictInformer(spDistrictInformer.getSelectedItemPosition());
                    crime.setCreatedAt(new Date());
                    imageProcessing.setImageWith_loader(ivCamera, imgPath);
                    databaseManager.insertCrime(crime);
                    Toast.makeText(activity, getResources().getString(R.string.successful_message), Toast.LENGTH_SHORT).show();
                    backToPrevious();
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.inform_terrorism), Toast.LENGTH_SHORT).show();
                }
                stopVideoAudioPlayer();
                break;
            case R.id.btnCancel:
                backToPrevious();
                stopVideoAudioPlayer();
                break;
        }
    }


    @SuppressLint("SetTextI18n")
    public String resultActivity(int resultCode, Intent data, TextView textView, LinearLayout linearLayout) {
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

                        linearLayout.setVisibility(View.VISIBLE);
                        if (textView == tvDocument) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(getString(R.string.document) + ": " + displayName);
                            //pdfUrl = displayName;
                            pdfUrl = data.getData();

                        } else if (textView == tvVideo) {
                            //playVideo(uri);
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(getString(R.string.video) + ": " + displayName);
                            uriVideo = data.getData();

                        } else if (textView == tvAudio) {
                            //playAudio(uri);
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(getString(R.string.audio) + ": " + displayName);
                            uriAudio = data.getData();
                        }


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

    public void playVideo(Uri uri) {
        videoView.setVideoURI(uri);
        videoView.requestFocus();
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
            if(llPDFView.isShown()){
                llPDFView.setVisibility(View.GONE);
                tvDocumentShowHide.setText(getString(R.string.show));
                isDocumentShow = true;
            }
            if(ivCamera.isShown()){
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
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(pdfURL));
        startActivity(intent);
*/
        pdfView.fromUri(pdfURL)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageScroll(this)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(activity))
                .load();

    }


   /* PDFView.Configurator.onRender(new OnRenderListener() {
        @Override
        public void onInitiallyRendered(int pages, float pageWidth, float pageHeight) {
            pdfView.fitToWidth(pageIndex);
        }
    });*/


    public void setImagePro(Bitmap bitmap, TextView textView) {
        //scale bitmap
        if (bitmap != null) {
            Bitmap b = (bitmap);
            //ivCamera.setVisibility(View.VISIBLE);
            imgPath = imgProc.imageSave(b);
            //textView.setText(imgPath);
            textView.setText(getString(R.string.picture) + ": " + imgPath);
            imgProc.setImageWith_loader(ivCamera, imgPath);
            b.recycle();
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
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.atu);
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
        //intent.putExtra("positionForm", positionForm);
        startActivity(intent);
        activity.finish();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }


    @Override
    public void onPageScrolled(int page, float positionOffset) {
        pageNumber = page;
    }

}
