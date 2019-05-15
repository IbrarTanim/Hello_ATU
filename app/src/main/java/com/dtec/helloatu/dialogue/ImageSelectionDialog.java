package com.dtec.helloatu.dialogue;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageButton;

import com.dtec.helloatu.R;
import com.dtec.helloatu.activities.FragmentBaseActivity;
import com.dtec.helloatu.fragment.AddInfoFragment;
import com.dtec.helloatu.utilities.MarshMallowPermission;

import static com.dtec.helloatu.utilities.PermissionCheck.hasPermissions;


public class ImageSelectionDialog extends Dialog implements View.OnClickListener {

    FragmentBaseActivity fragmentBaseActivity;
    AddInfoFragment addInfoFragment;
    Context context;
    ImageButton ibtnCamera, ibtnGallery, ibtnInternet;
    // flag for using item or task
    int flag;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.CAMERA
    };
    //MarshMallowPermission marshMallowPermission;

    public ImageSelectionDialog(Context context, FragmentBaseActivity fragmentBaseActivity, int flag) {

        // flag for using item or task
        super(context, R.style.CustomAlertDialog);
        this.context = context;
        this.fragmentBaseActivity = fragmentBaseActivity;
        this.flag = flag;
        addInfoFragment = new AddInfoFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_selection_dialog);

        ibtnCamera = (ImageButton) findViewById(R.id.ibtnCamera);
        ibtnGallery = (ImageButton) findViewById(R.id.ibtnGallery);
        ibtnInternet = (ImageButton) findViewById(R.id.ibtnInternet);

        //marshMallowPermission = new MarshMallowPermission(formActivity);


        ibtnCamera.setOnClickListener(this);
        ibtnGallery.setOnClickListener(this);
        ibtnInternet.setOnClickListener(this);


        // The request code used in ActivityCompat.requestPermissions()
// and returned in the Activity's onRequestPermissionsResult()


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnCamera:


                /*if (!formActivity.marshMallowPermission.checkPermissionForCamera()) {
                    formActivity.marshMallowPermission.requestPermissionForCamera();
                } else {
                    if (!formActivity.marshMallowPermission.checkPermissionForExternalStorage()) {
                        formActivity.marshMallowPermission.requestPermissionForExternalStorage();
                    } else {
                        formActivity.loadImageCamera();
                        formActivity.imageSelectionDialog.dismiss();
                    }
                }*/


                if (!hasPermissions(fragmentBaseActivity, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(fragmentBaseActivity, PERMISSIONS, PERMISSION_ALL);
                    //fragmentBaseActivity.loadImageCamera();
                    fragmentBaseActivity.loadImageCamera();
                    fragmentBaseActivity.imageSelectionDialog.dismiss();
                }


                break;

            case R.id.ibtnGallery:
                fragmentBaseActivity.loadImageGallery();
                fragmentBaseActivity.imageSelectionDialog.dismiss();
                break;

            case R.id.ibtnInternet:
                /*formActivity.loadImageInternet(flag);
                formActivity.imageSelectionDialog.dismiss();*/
                break;
        }

    }
}
