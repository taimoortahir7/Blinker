package com.example.tamoor.blinker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button submit_btn;
//    SeekBar seekBar;
    SwitchCompat switchCompat;
    LinearLayout seekBarLayout;
    Context context;
//    static int progressChangedValue = 0;
//    private blinkingSpeedInterface speedInterface;
    private PhoneListener phoneListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneListener = new PhoneListener(context);
//        speedInterface = phoneListener;
        initViews();
        setOnClickListeners();
        context = getBaseContext();
    }

    private void initViews() {
        submit_btn = findViewById(R.id.submit_btn);
//        seekBar = findViewById(R.id.simpleSeekBar);
        switchCompat = findViewById(R.id.switchButton);
        seekBarLayout = findViewById(R.id.seekBar_layout);
//        seekBar.setProgress(2);
    }

    private void setOnClickListeners() {
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    seekBarLayout.setVisibility(View.VISIBLE);
                } else {
                    seekBarLayout.setVisibility(View.GONE);
                }
            }
        });

//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                progressChangedValue = i;
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(MainActivity.this, "Progress value is : " + progressChangedValue, Toast.LENGTH_SHORT).show();
//            }
//        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                speedInterface.setSpeed(progressChangedValue);
                Intent intent = new Intent(context, BroadcastService.class);
                startService(intent);
            }
        });
    }

//    public interface blinkingSpeedInterface {
//        void setSpeed(int s);
//    }
}
