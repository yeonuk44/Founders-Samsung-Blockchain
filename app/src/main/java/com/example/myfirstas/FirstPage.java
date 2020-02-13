package com.example.myfirstas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.os.Handler;

import org.web3j.abi.datatypes.Int;

import jnr.ffi.annotations.In;

public class FirstPage extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            // 2초 뒤 다음 화면 넘어가기
            Intent intent = new Intent(getApplicationContext(), NameSet.class);
            startActivity(intent); // 다음 화면 넘어가기
            finish();

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page); // xml 과 java 소스를 연
    }

    @Override
    protected void onResume(){
        super.onResume();
        // 다시 화면에 들어왔을 때 예약 걸어주기
        handler.postDelayed(r, 2000); // 2초 뒤에 Runnable 객체 수행
    }

    @Override
    protected void onPause(){
        super.onPause();
        // 화면을 벗어나면, handler 에 예약해놓은 작업을 취소
        handler.removeCallbacks(r);
    }

}


