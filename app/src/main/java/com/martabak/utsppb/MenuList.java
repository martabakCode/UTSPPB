package com.martabak.utsppb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MenuList extends AppCompatActivity {
    CardView tambMenu,disMenu,lapMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        findid();
        tambMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuList.this,EditData.class));
            }
        });
        disMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuList.this,DisplayData.class));
            }
        });
        lapMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuList.this,LaporanBarang.class));
            }
        });
    }
    private void findid() {
        tambMenu=(CardView) findViewById(R.id.tambahMenu);
        disMenu=(CardView) findViewById(R.id.displayMenu);
        lapMenu=(CardView) findViewById(R.id.laporanMenu);
    }
}