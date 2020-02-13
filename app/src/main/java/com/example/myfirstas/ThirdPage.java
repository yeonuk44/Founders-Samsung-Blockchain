package com.example.myfirstas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThirdPage extends AppCompatActivity {

    Button thirdtosecondBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page);


        thirdtosecondBtn = findViewById(R.id.thirdtosecond);

        thirdtosecondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SecondPage.class);
                startActivity(intent);
            }
        });
    }
}
