package com.example.myfirstas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class SecondPage extends AppCompatActivity {

    Button playgameBtn;
    Button createroomBtn;
    Button secondtofirstBtn;
    Button secondtofifthBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        Toolbar toolbar = findViewById(R.id.toolbar);


        playgameBtn = findViewById(R.id.playgame);
        createroomBtn = findViewById(R.id.createroom);
        secondtofirstBtn = findViewById(R.id.secondtofirst);
        secondtofifthBtn = findViewById(R.id.secondtofifth);

        playgameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FourthPage.class);
                startActivity(intent);
            }
        });

        createroomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ThirdPage.class);
                startActivity(intent);
            }
        });

        secondtofirstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FirstPage.class);
                startActivity(intent);
            }
        });

        secondtofifthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FifthPage.class);
                startActivity(intent);
            }
        });
    }


}
