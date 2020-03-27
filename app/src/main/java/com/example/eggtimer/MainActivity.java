package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView timerView;
    private Button goButton;
    private SeekBar seekBar;
    private CountDownTimer countDownTimer;
    private int maxSec = 1500;
    private MediaPlayer mediaPlayer;
    private boolean isTicking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerView = (TextView) findViewById(R.id.timerView);
        goButton = (Button) findViewById(R.id.goButton);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier("airhorn", "raw", getPackageName()));
        seekBar.setMax(maxSec);
        seekBar.setProgress(maxSec / 2);
        timerView.setText(getStringTime(seekBar.getProgress()));


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                timerView.setText(getStringTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                countDownTimer.cancel();
                goButton.setText("GO!");
                isTicking = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });

        changeCountTimer(seekBar.getProgress());
    }


    public String getStringTime(int time) {
        String strMinutes = String.valueOf(time / 60).length() == 1 ? "0" + (time / 60) : String.valueOf(time / 60);
        String strSeconds = String.valueOf(time % 60).length() == 1 ? "0" + (time % 60) : String.valueOf(time % 60);

        return (strMinutes + ":" + strSeconds);
    }


    public void startTimer(View view) {



        if (!this.isTicking) {
            changeCountTimer(seekBar.getProgress());
            this.countDownTimer.start();
            this.isTicking = true;
            goButton.setText("STOP!");
        }else {
            this.isTicking = false;
            this.countDownTimer.cancel();
            goButton.setText("GO!");
        }

    }


    public void changeCountTimer(int sec) {
        this.countDownTimer = new CountDownTimer(sec * 1000+100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                seekBar.setProgress(seekBar.getProgress() - 1);

            }

            @Override
            public void onFinish() {
                goButton.setText("GO!");
                isTicking = false;
                mediaPlayer.start();

            }
        };
    }
}
