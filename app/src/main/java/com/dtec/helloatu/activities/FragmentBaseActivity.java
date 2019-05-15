package com.dtec.helloatu.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dtec.helloatu.R;
import com.dtec.helloatu.dialogue.ImageSelectionDialog;
import com.dtec.helloatu.fragment.AddInfoFragment;
import com.dtec.helloatu.fragment.EditInfoFragment;
import com.dtec.helloatu.utilities.CustomToast;
import com.dtec.helloatu.utilities.FileProcessing;
import com.dtec.helloatu.utilities.InternalStorageContentProvider;
import com.dtec.helloatu.utilities.MarshMallowPermission;
import com.dtec.helloatu.utilities.StaticAccess;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.dtec.helloatu.utilities.StaticAccess.MATERIAL_FILE_PICKER;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_AUDIO_REQUEST;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_FILE_REQUEST;
import static com.dtec.helloatu.utilities.StaticAccess.PICK_VIDEO_REQUEST;
import static com.dtec.helloatu.utilities.StaticAccess.REQUEST_CODE_TAKE_PICTURE;
import static com.dtec.helloatu.utilities.StaticAccess.SELECT_PICTURE;
import static com.dtec.helloatu.utilities.StaticAccess.TEMP_PHOTO_FILE_NAME;


public class FragmentBaseActivity extends FragmentActivity implements View.OnClickListener {

    FragmentBaseActivity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AddInfoFragment addInfoFragment;
    public int passedPosition;
    ImageView ivBack;
    public ImageSelectionDialog imageSelectionDialog;
    public String appImagePath = null;

    public String documentName;
    public String videoName;
    public String audioName;

    public MarshMallowPermission marshMallowPermission;
    FileProcessing fileProcessing;
    public String filePath;

    public File mFileTemp;
    public int intent_source = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        activity = this;

        marshMallowPermission = new MarshMallowPermission(activity);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            passedPosition = bundle.getInt("positionFragmentBaseActivity");
        }
        //Toast.makeText(activity, String.valueOf(passedPosition), Toast.LENGTH_SHORT).show();

    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(R.string.new_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tabOne.setTextSize(25);
        }
        tabOne.setTextColor(Color.WHITE);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.info_edit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tabTwo.setTextSize(25);
        }
        tabTwo.setTextColor(Color.WHITE);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

    }

    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AddInfoFragment(), getString(R.string.new_info));
        adapter.addFrag(new EditInfoFragment(), getString(R.string.info_edit));
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

        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
        finish();

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        addInfoFragment = (AddInfoFragment) getSupportFragmentManager().getFragments().get(0);
        imageSelection(requestCode, resultCode, data);

        switch (requestCode) {

            case PICK_FILE_REQUEST:
                documentName = addInfoFragment.resultActivity(resultCode, data, addInfoFragment.tvDocument);
                break;
            case PICK_VIDEO_REQUEST:
                videoName = addInfoFragment.resultActivity(resultCode, data, addInfoFragment.tvVideo);
                break;
            case PICK_AUDIO_REQUEST:
                audioName = addInfoFragment.resultActivity(resultCode, data, addInfoFragment.tvAudio);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void imageSelection(int requestCode, int resultCode, Intent data) {

        addInfoFragment = (AddInfoFragment) getSupportFragmentManager().getFragments().get(0);

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
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                addInfoFragment.setImagePro(bitmap);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
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
        addInfoFragment.setImagePro(widgetImage);
        intent_source = 0;

    }

    // Opening Image Cropper (Transparent)
    public void openCropper(Uri uri) {

        com.theartofdev.edmodo.cropper.CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .start(activity);
    }

    // load image from Gallery by Rokan
    public void loadImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

        intent_source = 1;
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
            activity.mFileTemp = new File(Environment.getExternalStorageDirectory() + appImagePath, TEMP_PHOTO_FILE_NAME);
        } else {
            activity.mFileTemp = new File(activity.getFilesDir() + appImagePath, TEMP_PHOTO_FILE_NAME);
        }
        //musicControl = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            Uri mImageCaptureUri = null;
            String state2 = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state2)) {
                mImageCaptureUri = Uri.fromFile(activity.mFileTemp);
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

    private void loadFragment() {
        addInfoFragment = new AddInfoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transactionMonth = fragmentManager.beginTransaction();
        transactionMonth.addToBackStack(null);
        transactionMonth.replace(R.id.flContentView, addInfoFragment, "AddInfoFragment").commit();
    }

}


