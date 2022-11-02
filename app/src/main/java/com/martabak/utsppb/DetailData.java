package com.martabak.utsppb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.martabak.utsppb.DBMain;
import com.martabak.utsppb.PrintData;
import com.martabak.utsppb.R;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DetailData extends AppCompatActivity {
    NumberFormat formatter = new DecimalFormat("#,###");
    TextView title,satuan,harga,terjualTv;
    ImageView image;
    Button btnPesan;
    EditText nama,alamat,pekerjaan,jumlah;
    DBMain dBmain;
    SQLiteDatabase sqLiteDatabase;
    int kode, hargaData,jumlahTerjual;
    String satuanData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_data);
        findId();
        dBmain=new DBMain(this);
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
                image.setImageBitmap(bitmap);
                //for set name
                title.setText(cursor.getString(1));
                //for set satuan
                jumlahTerjual = cursor.getInt(6);
                satuan.setText(String.valueOf(cursor.getInt(4)+" "+cursor.getString(2)));
                terjualTv.setText("Terjual : "+String.valueOf(cursor.getInt(6)+" "+cursor.getString(2)));
                satuanData = cursor.getString(2);
                //for set harga
                hargaData = cursor.getInt(3);
                harga.setText("Rp."+formatter.format(cursor.getInt(3)));
                //for set jumlah
            }
        }
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv=new ContentValues();
                cv.put("terjual",jumlahTerjual+Integer.parseInt(jumlah.getText().toString()));

                sqLiteDatabase=dBmain.getWritableDatabase();
                long recedit=sqLiteDatabase.update("barang",cv,"kode="+kode,null);
                if (recedit!=-1){
                    Intent i=new Intent(DetailData.this, PrintData.class);
                    i.putExtra("nama",nama.getText().toString());
                    i.putExtra("alamat",alamat.getText().toString());
                    i.putExtra("pekerjaan",pekerjaan.getText().toString());
                    i.putExtra("jumlah",jumlah.getText().toString());
                    i.putExtra("barang",title.getText().toString());
                    i.putExtra("harga",hargaData);
                    i.putExtra("satuan",satuanData);
                    startActivity(i);
                }

            }
        });
    }
    private void findId() {
        title = findViewById(R.id.textViewTitle);
        satuan = findViewById(R.id.textViewSatuan);
        harga = findViewById(R.id.textViewHarga);
        image = findViewById(R.id.imageView);
        btnPesan = findViewById(R.id.buttonPesan);
        nama = findViewById(R.id.InNama);
        alamat = findViewById(R.id.InAlamat);
        pekerjaan = findViewById(R.id.InPekerjaan);
        jumlah = findViewById(R.id.InJumlah);
        terjualTv = findViewById(R.id.textViewTerjual);

    }
    private byte[] ImageViewToByte(ImageView avatar) {
        Bitmap bitmap=((BitmapDrawable)avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[]bytes=stream.toByteArray();
        return bytes;
    }
}