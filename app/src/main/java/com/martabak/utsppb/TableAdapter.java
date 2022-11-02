package com.martabak.utsppb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder>{
    Context context;
    int tabledata;
    ArrayList<Model> modelArrayList;
    NumberFormat formatter = new DecimalFormat("#,###");
    SQLiteDatabase sqLiteDatabase;
    //generate constructor

    public TableAdapter(Context context, int tabledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.tabledata = tabledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public TableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.tabledata,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int p) {
        final Model model=modelArrayList.get(p);
        byte[]image=model.getGambar();
        Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
        holder.imageavatar.setImageBitmap(bitmap);
        holder.txtname.setText(model.getNama());
        holder.txtsatuan.setText(model.getSatuan());
        holder.txtharga.setText("Rp."+formatter.format(model.getHarga()));
        holder.txtjumlah.setText(String.valueOf(model.getJumlah()));
        holder.txtTerjual.setText(String.valueOf(model.getTerjual()));
        holder.txtSisa.setText(String.valueOf(model.getJumlah()-model.getTerjual()));
    }


    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageavatar;
        TextView txtname,txtsatuan,txtharga,txtjumlah,txtTerjual,txtSisa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageavatar=(ImageView)itemView.findViewById(R.id.viewavatarTable);
            txtname=(TextView)itemView.findViewById(R.id.nama);
            txtsatuan=(TextView)itemView.findViewById(R.id.satuan);
            txtharga=(TextView)itemView.findViewById(R.id.harga);
            txtjumlah=(TextView)itemView.findViewById(R.id.stok);
            txtTerjual=(TextView)itemView.findViewById(R.id.terjual);
            txtSisa=(TextView)itemView.findViewById(R.id.sisa);
        }
    }
}
