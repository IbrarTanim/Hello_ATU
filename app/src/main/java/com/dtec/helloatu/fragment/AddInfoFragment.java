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
import android.util.Base64;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import com.dtec.helloatu.utilities.FileProcessing;
import com.dtec.helloatu.utilities.ImageProcessing;
import com.dtec.helloatu.utilities.MarshMallowPermission;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_AUDIO_REQUEST;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_FILE_REQUEST;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_VIDEO_REQUEST;

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
    EditText etCrimeInfo, etInformerName, etInformerPhone, etInformerAddress;
    PDFView pdfView;
    LinearLayout llPDFView;
    public LinearLayout llDocument, llCamera, llVideo, llAudio;
    public TextView tvDocument, tvCamera, tvVideo, tvAudio, tvCrimeTitle;
    TextView tvDocumentShowHide, tvPicShowHide, tvVideoPlayStop, tvAudioPlayStop;
    FileProcessing fileProcessing;
    List<DistrictMain> listDistrictMain;
    List<MohanogorMain> mohanogorMains;

    public ImageView ivCamera;
    String imgPath = "";
    String byteConvertedImage;
    String videoCheck;
    Crime crime;
    String strFile;
    ImageProcessing imgProc;
    String displayName;
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

    String itemOccurrence;

    boolean isDocumentShow = false;
    boolean isPicsShow = false;
    boolean isVideoPlay = false;
    boolean isAudioPlay = false;

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
    List<String> listMohanogorThana;
    List<String> listCountry;


   /* List<String> thanaDhaka;
    List<String> dstDhaka;
    List<String> dstChittagong;
    List<String> dstSylhet;

    List<String> mohanogorDhaka;
    List<String> mohanogorChittagong;
    List<String> mohanogorSylhet;*/


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

        spDistrict.setOnItemSelectedListener(this);
        spDistrictInformer.setOnItemSelectedListener(this);
        spThana.setOnItemSelectedListener(this);
        spThanaInformer.setOnItemSelectedListener(this);
        spDimoutInformer.setOnItemSelectedListener(this);
        spDimout.setOnItemSelectedListener(this);

        isDocumentShow = true;
        isPicsShow = true;
        isVideoPlay = true;
        isAudioPlay = true;


        getDistrictJsonFile();
        getMohanogorJsonFile();
        getCountryJsonFile();

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
                    //String itemDistrict = parent.getItemAtPosition(position).toString();
                    List<String> thana = getThanaFromJson(itemDistrict);
                    thanaAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, thana);
                    thanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spThana.setAdapter(thanaAdapter);
                    spThana.setVisibility(View.VISIBLE);
                    spDistrict.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.spThana:
                String itemThana = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spDimoutInformer:
                String itemOccurrenceInformer = parent.getItemAtPosition(position).toString();
                if (itemOccurrenceInformer == getString(R.string.mohanogor)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listMohanogor);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrictInformer.setAdapter(districtAdapter);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    spThanaInformer.setVisibility(View.GONE);
                    // spThanaInformer.setVisibility(View.GONE);
                } else if (itemOccurrenceInformer == getString(R.string.district)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listDistrict);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrictInformer.setAdapter(districtAdapter);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    spThanaInformer.setVisibility(View.GONE);
                    //spThana.setVisibility(View.GONE);
                } else if (itemOccurrenceInformer == getString(R.string.bdout)) {
                    districtAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listCountry);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrictInformer.setAdapter(districtAdapter);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    spThanaInformer.setVisibility(View.GONE);
                    //spThana.setVisibility(View.GONE);
                }
                break;

            case R.id.spDistrictInformer:
                String itemDistrictInformer = parent.getItemAtPosition(position).toString();
                if (itemDistrictInformer == getString(R.string.div_dhaka)) {
                    thanaAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listDistrictThana);
                    thanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spThanaInformer.setAdapter(thanaAdapter);
                    spThanaInformer.setVisibility(View.VISIBLE);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    //spThana.setVisibility(View.GONE);
                } else if (itemDistrictInformer == getString(R.string.div_chittagong)) {
                    thanaAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listDistrictThana);
                    thanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spThanaInformer.setAdapter(thanaAdapter);
                    spThanaInformer.setVisibility(View.VISIBLE);
                    spDistrictInformer.setVisibility(View.VISIBLE);
                    //spThana.setVisibility(View.GONE);
                } else if (itemDistrictInformer == getString(R.string.div_sylhet)) {
                    thanaAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, listDistrictThana);
                    thanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spThanaInformer.setAdapter(thanaAdapter);
                    spThanaInformer.setVisibility(View.VISIBLE);
                    spDistrictInformer.setVisibility(View.VISIBLE);
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
        scrollViewAddInfo.fullScroll(scrollViewAddInfo.FOCUS_DOWN);
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

            case R.id.btnSubmit:
                if (etCrimeInfo.getText().length() > 0) {
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
                break;
            case R.id.btnCancel:
                backToPrevious();
                stopVideoAudioPlayer();
                break;
        }
    }


    @SuppressLint("SetTextI18n")
    public byte[] resultActivity(int resultCode, Intent data, TextView textView, LinearLayout linearLayout) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();

            try {
                fullyReadFileToBytes(myFile);
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = activity.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        displayByte = uriTobyte(data.getData());


                        linearLayout.setVisibility(View.VISIBLE);
                        if (textView == tvDocument) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(getString(R.string.document) + ": " + displayName);
                            pdfUrl = data.getData();
                            //uriTobyte(pdfUrl);


                        } else if (textView == tvVideo) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(getString(R.string.video) + ": " + displayName);
                            uriVideo = data.getData();


                        } else if (textView == tvAudio) {
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
        //return displayName;
        return displayByte;
    }

    private byte[] uriTobyte(Uri data) {
        //Uri data = result.getData();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(data.getPath()));
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }


    byte[] fullyReadFileToBytes(File f) throws IOException {
        int size = (int) f.length();
        byte bytes[] = new byte[size];
        byte tmpBuff[] = new byte[size];
        FileInputStream fis = new FileInputStream(f);
        ;
        try {

            int read = fis.read(bytes, 0, size);
            if (read < size) {
                int remain = size - read;
                while (remain > 0) {
                    read = fis.read(tmpBuff, 0, remain);
                    System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
                    remain -= read;
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            fis.close();
        }

        return bytes;

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

    public void setImagePro(Bitmap bitmap, TextView textView) {
        //scale bitmap
        if (bitmap != null) {
            Bitmap b = (bitmap);
            byteConvertedImage = Base64.encodeToString(imgProc.getBytesFromBitmap(bitmap), Base64.NO_WRAP);


            imgPath = imgProc.imageSave(b);
            textView.setText(getString(R.string.picture) + ": " + byteConvertedImage);
            imgProc.setImageWith_loader(ivCamera, byteConvertedImage);
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
        startActivity(intent);
        activity.finish();
    }

    public byte[] convert(String path) throws IOException {

        FileInputStream fis = new FileInputStream(path);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];

        for (int readNum; (readNum = fis.read(b)) != -1; ) {
            bos.write(b, 0, readNum);
        }

        byte[] bytes = bos.toByteArray();

        return bytes;
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
            Log.e("asdasd", mohanogorMains.toString());

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


}
