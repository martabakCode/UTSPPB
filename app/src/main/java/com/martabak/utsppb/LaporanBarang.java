package com.martabak.utsppb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class LaporanBarang extends AppCompatActivity {
    DBMain dBmain;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    TableAdapter tabAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_barang);
        dBmain=new DBMain(this);
        findId();
//        Log.i("heree", "displayData: ");
        displayData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }
    private void displayData() {
        sqLiteDatabase=dBmain.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from barang",null);
        ArrayList<Model> models=new ArrayList<>();
        while (cursor.moveToNext()){
            int kode=cursor.getInt(0);
//            Log.i(String.valueOf(kode), "displayData: ");
            String nama=cursor.getString(1);
            String satuan=cursor.getString(2);
            int harga=cursor.getInt(3);
            int jumlah=cursor.getInt(4);
            int terjual=cursor.getInt(6);
            byte[]avatar=cursor.getBlob(5);
            models.add(new Model(kode,nama,satuan,harga,jumlah,avatar,terjual));
        }
        cursor.close();
        tabAdapter=new TableAdapter(this,R.layout.tabledata,models,sqLiteDatabase);
        recyclerView.setAdapter(tabAdapter);
    }

    private void findId() {
        recyclerView=findViewById(R.id.rv);
    }
}