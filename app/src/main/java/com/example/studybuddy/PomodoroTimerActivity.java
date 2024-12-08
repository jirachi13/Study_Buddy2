package com.example.studybuddy;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PomodoroTimerActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button startButton;
    private CountDownTimer timer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 1500000; // 25 minutes in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_timer);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);

        updateTimerText();

        startButton.setOnClickListener(v -> {
            if (isTimerRunning) {
                stopTimer();
            } else {
                startTimer();
            }
        });
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                startButton.setText("Start");
            }
        }.start();

        isTimerRunning = true;
        startButton.setText("Stop");
    }

    private void stopTimer() {
        timer.cancel();
        isTimerRunning = false;
        startButton.setText("Start");
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);
    }
}
