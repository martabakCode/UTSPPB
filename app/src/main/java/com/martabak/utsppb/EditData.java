package com.martabak.utsppb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

public class EditData extends AppCompatActivity {
    DBMain dBmain;
    SQLiteDatabase sqLiteDatabase;
    ImageView avatar;
    EditText nama,satuan,harga,jumlah;
    Button submit,edit;
    int kode=0;

    public static final int CAMERA_REQUEST=100;
    public static final int STORAGE_REQUEST=101;
    String[]cameraPermission;
    String[]storagePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        dBmain=new DBMain(this);

        findid();
        insertData();
        imagePick();
        editData();

    }
    private void editData() {
        if (getIntent().getSerializableExtra("kode")!=null){
            int kodeEdit = Integer.parseInt(String.valueOf(getIntent().getSerializableExtra("kode")));
            String queryString =
                    "select * from barang where kode="+kodeEdit;
            sqLiteDatabase=dBmain.getReadableDatabase();
            Cursor cursor=sqLiteDatabase.rawQuery(queryString,null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                kode = cursor.getInt(0);
                //for image
                byte[] bytes = cursor.getBlob(5);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                avatar.setImageBitmap(bitmap);
                //for set name
                nama.setText(cursor.getString(1));
                //for set satuan
                satuan.setText(cursor.getString(2));
                //for set harga
                harga.setText(String.valueOf(cursor.getInt(3)));
                //for set jumlah
                jumlah.setText(String.valueOf(cursor.getInt(4)));
            }
            //visible edit button and hide submit button
            submit.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
        }
    }
    private void insertData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv=new ContentValues();
                cv.put("gambar",ImageViewToByte(avatar));
                cv.put("nama",nama.getText().toString());
                cv.put("satuan",satuan.getText().toString());
                cv.put("harga",harga.getText().toString());
                cv.put("jumlah",jumlah.getText().toString());
                sqLiteDatabase=dBmain.getWritableDatabase();
                Long recinsert=sqLiteDatabase.insert("barang",null,cv);
                if (recinsert!=null){
                    startActivity(new Intent(EditData.this,DisplayData.class));
                }
            }
        });
        //for storing new data or update data
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv=new ContentValues();
                cv.put("gambar",ImageViewToByte(avatar));
                cv.put("nama",nama.getText().toString());
                cv.put("satuan",satuan.getText().toString());
                cv.put("harga",harga.getText().toString());
                cv.put("jumlah",jumlah.getText().toString());
                sqLiteDatabase=dBmain.getWritableDatabase();
                long recedit=sqLiteDatabase.update("barang",cv,"kode="+kode,null);
                if (recedit!=-1){
                    startActivity(new Intent(EditData.this,DisplayData.class));
                }
            }
        });
    }

    //Define id
    private void findid() {
        avatar=(ImageView)findViewById(R.id.avatar);
        nama=(EditText)findViewById(R.id.edit_nama);
        satuan=(EditText)findViewById(R.id.edit_satuan);
        harga=(EditText)findViewById(R.id.edit_harga);
        jumlah=(EditText)findViewById(R.id.edit_jumlah);
        submit=(Button)findViewById(R.id.btn_submit);
        edit=(Button)findViewById(R.id.btn_edit);
    }

    private void imagePick() {
        avatar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int avatar=0;
                if (avatar==1){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else {
                        pickFromGallery();
                    }
                }else if (avatar==0){
                    Log.i( "here", "onClick: ");
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(storagePermission,STORAGE_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void pickFromGallery() {
        CropImage.activity().start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        Log.i(String.valueOf(cameraPermission), "requestCameraPermission: ");
        requestPermissions(cameraPermission,CAMERA_REQUEST);

    }

    private boolean checkCameraPermission() {
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        boolean result2=ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        return result && result2;
    }

    private byte[] ImageViewToByte(ImageView avatar) {
        Bitmap bitmap=((BitmapDrawable)avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[]bytes=stream.toByteArray();
        return bytes;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST:{
                Log.i(String.valueOf(CAMERA_REQUEST), "onRequestPermissionsResult: ");
                if (grantResults.length>0){
                    boolean camera_accept=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean storage_accept=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if (camera_accept&&storage_accept){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, "enable camera and storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST:{
                if (grantResults.length>0){
                    boolean storage_accept=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if (storage_accept){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, "please enable storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }
    //overrid method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                Uri resultUri=result.getUri();
                Picasso.with(this).load(resultUri).into(avatar);
            }
        }
    }
}