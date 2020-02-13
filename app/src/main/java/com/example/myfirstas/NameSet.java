package com.example.myfirstas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import jnr.ffi.annotations.In;


public class NameSet extends AppCompatActivity {


    Button namesetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_set);



        namesetBtn = findViewById(R.id.namesubmit);


        namesetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SecondPage.class);
                startActivity(intent);
            }
        });
    }
}
