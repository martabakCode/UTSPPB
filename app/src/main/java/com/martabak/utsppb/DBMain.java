package com.martabak.utsppb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBMain extends SQLiteOpenHelper {
    public static final String DBNAME = "jualbeli.db";
    public static final String TABLENAME = "barang";
    public static final int VER = 1;

    public DBMain(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table " + TABLENAME + "(kode integer primary key, nama text, satuan varchar, harga integer, jumlah integer, gambar blob,terjual integer)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "drop table if exists " + TABLENAME + "";
        sqLiteDatabase.execSQL(query);
    }
}
