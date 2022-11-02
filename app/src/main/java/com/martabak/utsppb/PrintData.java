package com.martabak.utsppb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PrintData extends AppCompatActivity {

    TextView nama,alamat,pekerjaan,barang,harga,jumlah,total,ppn,bayar;
    int totalData;
    double totalPPN,totalBayar;
    NumberFormat formatter = new DecimalFormat("#,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_data);
        nama = findViewById(R.id.InNama);
        alamat = findViewById(R.id.InAlamat);
        pekerjaan = findViewById(R.id.InPekerjaan);
        barang = findViewById(R.id.InUnit);
        harga = findViewById(R.id.InHarga);
        jumlah = findViewById(R.id.InJumlah);
        total = findViewById(R.id.InTotal);
        ppn = findViewById(R.id.InPPN);
        bayar = findViewById(R.id.InBayar);
        Button back = findViewById(R.id.btnBack);


        if(getIntent()!=null){
            nama.setText(String.valueOf(getIntent().getSerializableExtra("nama")));
            alamat.setText(String.valueOf(getIntent().getSerializableExtra("alamat")));
            pekerjaan.setText(String.valueOf(getIntent().getSerializableExtra("pekerjaan")));
            barang.setText(String.valueOf(getIntent().getSerializableExtra("barang")));
            harga.setText("Rp."+formatter.format(getIntent().getSerializableExtra("harga")));
            jumlah.setText(String.valueOf(getIntent().getSerializableExtra("jumlah"))+" "+String.valueOf(getIntent().getSerializableExtra("satuan")));
            totalData = Integer.parseInt(String.valueOf(getIntent().getSerializableExtra("jumlah")))*Integer.parseInt(String.valueOf(getIntent().getSerializableExtra("harga")));
            total.setText("Rp."+formatter.format(totalData));
            totalPPN = totalData*0.11;
            totalBayar = totalData+totalPPN;
            ppn.setText("Rp."+formatter.format(totalPPN));
            bayar.setText("Rp."+formatter.format(totalBayar));
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PrintData.this, MenuList.class);
                startActivity(i);
            }
        });
    }
}