package com.dtec.helloatu.utilities;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dtec.helloatu.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DeppDemo extends AppCompatActivity {

    EditText fatchValue;
    TextView displayValue, Fpath;
    Button GetValue, SelectFile;
    String ret, uriString, finalString;
    File myFile;
    String line = null;
    private static final int PICKFILE_RESULT_CODE = 1;
    public static final int RESULT_OK = -1;
    Uri uri;
    byte[] fileBytes;
    StringBuilder total = new StringBuilder();
    byte[] bytes;
    ByteArrayOutputStream output;


    String[] permissions = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            uri = data.getData();
            uriString = uri.toString();

            try {
                InputStream in = getContentResolver().openInputStream(uri);
                bytes = getBytes(in);
                String nameData = Base64.encodeToString(bytes, Base64.DEFAULT);
                Toast.makeText(this, nameData, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


}