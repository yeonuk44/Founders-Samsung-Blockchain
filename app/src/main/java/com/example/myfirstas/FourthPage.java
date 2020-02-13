package com.example.myfirstas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FourthPage extends AppCompatActivity {


    Button chichenBtn;
    Button fourthtosecondBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_page);

        chichenBtn = findViewById(R.id.chichen);
        fourthtosecondBtn = findViewById(R.id.fourthtosecond);


        chichenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WaitRoom.class);
                startActivity(intent);
            }
        });



        fourthtosecondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),SecondPage.class);
                startActivity(intent);
            }
        });
    }
}
