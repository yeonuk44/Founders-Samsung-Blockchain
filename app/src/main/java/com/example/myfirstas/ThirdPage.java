package com.example.myfirstas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


public class ThirdPage extends AppCompatActivity {

    SeekBar seekBar;
    TextView tvOut;
    Button thirdtosecondBtn;

    public int number = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page);

        // Spinner
        Spinner itemSpinner = (Spinner)findViewById(R.id.itemSelect);
        ArrayAdapter itemAdapter =ArrayAdapter.createFromResource(this,
                R.array.itemselect, android.R.layout.simple_spinner_item);
            itemAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        itemSpinner.setAdapter(itemAdapter);


        // Seekbar
        seekBar = (SeekBar) findViewById(R.id.mseekbar);
        tvOut = (TextView) findViewById(R.id.tvout);

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                number = seekBar.getProgress();
                update();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                number = seekBar.getProgress();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                number = seekBar.getProgress();

            }
        });






        thirdtosecondBtn = findViewById(R.id.thirdtosecond);

        thirdtosecondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SecondPage.class);
                startActivity(intent);
            }
        });
    }

    public void update(){
        tvOut.setText(new StringBuilder().append(number));
    }
}
