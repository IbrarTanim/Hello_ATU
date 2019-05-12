package com.dtec.helloatu.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dtec.helloatu.R;
import com.dtec.helloatu.fragment.AddInfoFragment;
import com.dtec.helloatu.fragment.EditInfoFragment;
import com.dtec.helloatu.utilities.CustomToast;
import com.dtec.helloatu.utilities.FileProcessing;
import com.dtec.helloatu.utilities.ImageProcessing;
import com.dtec.helloatu.utilities.InternalStorageContentProvider;
import com.dtec.helloatu.utilities.StaticAccess;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentBaseActivity extends FragmentActivity implements View.OnClickListener {

    FragmentBaseActivity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public AddInfoFragment addInfoFragment;
    int passedPosition;
    ImageView ivBack;
   // public ImageSelectionDialog imageSelectionDialog;
    public File mFileTemp;
    private String appImagePath = null;
    ImageProcessing imgProc;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x8;
    private static final int SELECT_PICTURE = 0x1;
    private static final int PICK_FILE_REQUEST = 0x5;
    private static final int PICK_VIDEO_REQUEST = 0x9;
    private static final int PICK_AUDIO_REQUEST = 0x4;

    int intent_source = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        activity = this;

       // addInfoFragment = (AddInfoFragment)getFragmentManager().findFragmentById(R.id.flContentView);
        addInfoFragment = (AddInfoFragment) getSupportFragmentManager().findFragmentById(R.id.flContentView);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        imgProc = new ImageProcessing(activity);
        appImagePath = imgProc.getImageDir();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            passedPosition = bundle.getInt("positionFragmentActivity");
        }
        Toast.makeText(activity, String.valueOf(passedPosition), Toast.LENGTH_SHORT).show();

    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(R.string.new_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        tabOne.setTextColor(Color.WHITE);
        //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_add_info, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.info_edit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        tabTwo.setTextColor(Color.WHITE);
        //tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_info_edit, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

    }


    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AddInfoFragment(), "নতুন তথ্য");
        adapter.addFrag(new EditInfoFragment(), "তথ্য সংশোধন");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {

        finish();

    }




    /*



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




*/


}
