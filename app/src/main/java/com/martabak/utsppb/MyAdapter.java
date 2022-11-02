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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    Context context;
    int singledata;
    NumberFormat formatter = new DecimalFormat("#,###");
    ArrayList<Model> modelArrayList;
    SQLiteDatabase sqLiteDatabase;
    //generate constructor

    public MyAdapter(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.singeldata,null);
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
        holder.txtjumlah.setText(String.valueOf(model.getJumlah()-model.getTerjual()));
        holder.btnDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, DetailData.class);
                i.putExtra("kode",model.getKode());
                context.startActivity(i);
            }
        });
        //flow menu
        holder.flowmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.flowmenu);
                popupMenu.inflate(R.menu.flow_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_menu:
                                ///////
                                //edit operation
//                                Bundle bundle=new Bundle();
//                                bundle.putInt("kode",model.getKode());
//                                bundle.putByteArray("gambar",model.getGambar());
//                                bundle.putString("nama",model.getNama());
//                                bundle.putString("satuan",model.getSatuan());
//                                bundle.putInt("harga",model.getHarga());
//                                bundle.putInt("jumlah",model.getJumlah());
//                                Toast.makeText(context, bundle.getString("nama"), Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(context,EditData.class);
                                i.putExtra("kode",model.getKode());
                                context.startActivity(i);
                                break;
                            case R.id.delete_menu:
                                ///delete operation
                                DBMain dBmain=new DBMain(context);
                                sqLiteDatabase=dBmain.getReadableDatabase();
                                long recdelete=sqLiteDatabase.delete("barang","kode="+model.getKode(),null);
                                if (recdelete!=-1){
                                    Toast.makeText(context, "data deleted", Toast.LENGTH_SHORT).show();
                                    //remove position after deleted
                                    modelArrayList.remove(p);
                                    //update data
                                    notifyDataSetChanged();
                                }
                                break;
                            default:
                                return false;
                        }
                        return false;
                    }
                });
                //display menu
                popupMenu.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageavatar;
        TextView txtname,txtsatuan,txtharga,txtjumlah;
        Button btnDet;
        ImageButton flowmenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageavatar=(ImageView)itemView.findViewById(R.id.viewavatar);
            txtname=(TextView)itemView.findViewById(R.id.txt_name);
            txtsatuan=(TextView)itemView.findViewById(R.id.txt_satuan);
            txtharga=(TextView)itemView.findViewById(R.id.txt_harga);
            txtjumlah=(TextView)itemView.findViewById(R.id.txt_jumlah);
            flowmenu=(ImageButton)itemView.findViewById(R.id.flowmenu);
            btnDet=(Button) itemView.findViewById(R.id.detailBtn);
        }
    }
}
