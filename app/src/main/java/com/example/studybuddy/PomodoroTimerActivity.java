package com.example.studybuddy;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PomodoroTimerActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button startButton;
    private Button setTimeButton;
    private EditText timeInputEditText;
    private CountDownTimer timer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 1500000; // Default: 25 minutes in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_timer);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        setTimeButton = findViewById(R.id.setTimeButton);
        timeInputEditText = findViewById(R.id.timeInputEditText);

        updateTimerText();

        startButton.setOnClickListener(v -> {
            if (isTimerRunning) {
                stopTimer();
            } else {
                startTimer();
            }
        });

        setTimeButton.setOnClickListener(v -> {
            String inputText = timeInputEditText.getText().toString();
            if (!inputText.isEmpty()) {
                try {
                    int minutes = Integer.parseInt(inputText);
                    if (minutes > 0) {
                        setTimer(minutes);
                    } else {
                        Toast.makeText(PomodoroTimerActivity.this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(PomodoroTimerActivity.this, "Invalid input, please enter a number", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PomodoroTimerActivity.this, "Please enter a time", Toast.LENGTH_SHORT).show();
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

    private void setTimer(int minutes) {
        timeLeftInMillis = minutes * 60000; // Convert minutes to milliseconds
        updateTimerText();
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);
    }
}
