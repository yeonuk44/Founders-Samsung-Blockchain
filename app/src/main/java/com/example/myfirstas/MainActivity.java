package com.example.myfirstas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {




    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            // 1.5초 뒤 다음 하면 넘어가기
            Intent intent = new Intent(getApplicationContext(), FirstPage.class);
            startActivity(intent); // 다음 화면 넘어가기
            finish();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main); // xml 과 java 소스를 연
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







    }


    @Override
    protected void onResume(){
        super.onResume();
        // 다시 화면에 들어왔을 때 예약 걸어주기
        handler.postDelayed(r, 1500); // 1.5초 뒤에 Runnable 객체 수
    }

    @Override
    protected void onPause(){
        super.onPause();
        // 화면을 벗어나면, handler 에 예약해놓은 작업 취소.
    }












}
