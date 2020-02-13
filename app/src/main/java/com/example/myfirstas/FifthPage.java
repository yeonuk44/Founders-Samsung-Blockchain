package com.example.myfirstas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import jnr.ffi.annotations.In;

public class FifthPage extends AppCompatActivity {

    Button rewardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth_page);

        rewardBtn = findViewById(R.id.fifthtoreward);

        rewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reward.class);
                startActivity(intent);
            }
        });
    }
}
