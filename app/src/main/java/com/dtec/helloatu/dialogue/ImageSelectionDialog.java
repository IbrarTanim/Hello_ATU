package com.dtec.helloatu.dialogue;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.dtec.helloatu.R;
import com.dtec.helloatu.activities.FormActivity;


public class ImageSelectionDialog extends Dialog implements View.OnClickListener {

    FormActivity formActivity;
    Context context;
    ImageButton ibtnCamera, ibtnGallery, ibtnInternet;
    // flag for using item or task
    int flag;
    public ImageSelectionDialog(Context context, FormActivity formActivity, int flag) {

        // flag for using item or task
        super(context, R.style.CustomAlertDialog);
        this.context = context;
        this.formActivity = formActivity;
        this.flag=flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_selection_dialog);

        ibtnCamera = (ImageButton) findViewById(R.id.ibtnCamera);
        ibtnGallery = (ImageButton) findViewById(R.id.ibtnGallery);
        ibtnInternet = (ImageButton) findViewById(R.id.ibtnInternet);



        ibtnCamera.setOnClickListener(this);
        ibtnGallery.setOnClickListener(this);
        ibtnInternet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ibtnCamera:
                formActivity.loadImageCamera();
                formActivity.imageSelectionDialog.dismiss();
                break;

            case R.id.ibtnGallery:
                formActivity.loadImageGallery();
                formActivity.imageSelectionDialog.dismiss();
                break;

            case R.id.ibtnInternet:
                /*formActivity.loadImageInternet(flag);
                formActivity.imageSelectionDialog.dismiss();*/
                break;
        }

    }
}
