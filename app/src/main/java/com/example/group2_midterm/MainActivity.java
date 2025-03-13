package com.example.group2_midterm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group2_midterm.utility.Constants;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText editTextWorkoutType;
    private Button buttonStartStop;
    private TextView textViewTimer;

    private boolean isRunning = false;
    private long startTime, timeInMillis = 0L;
    private final Handler handler = new Handler();

    private final Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMillis = SystemClock.elapsedRealtime() - startTime;
            int seconds = (int) (timeInMillis / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            textViewTimer.setText(String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60));
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextWorkoutType = findViewById(R.id.editTextWorkoutType);
        buttonStartStop = findViewById(R.id.buttonStartStop);
        textViewTimer = findViewById(R.id.textViewTimer);

        buttonStartStop.setOnClickListener(v -> {
            if (!isRunning) {
                startWorkout();
            } else {
                stopWorkout();
            }
        });
    }

    private void startWorkout() {
        startTime = SystemClock.elapsedRealtime();
        handler.postDelayed(updateTimerThread, 0);
        isRunning = true;
        buttonStartStop.setText(R.string.stop);
    }

    private void stopWorkout() {
        handler.removeCallbacks(updateTimerThread);
        String workoutType = editTextWorkoutType.getText().toString().trim();
        if (workoutType.isEmpty()) {
            Toast.makeText(this, "Please enter a valid workout type.", Toast.LENGTH_SHORT).show();
            return;
        }
        int totalMinutes = (int) ((timeInMillis / 1000) / 60);  // Convert to minutes
        float caloriesBurned = calculateCalories(workoutType, totalMinutes);

        // Save workout details to SharedPreferences
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> workoutSet = new HashSet<>(prefs.getStringSet(Constants.WORKOUT_KEY, new HashSet<>()));
        workoutSet.add(workoutType + " - " + totalMinutes + " minutes  Calories Burned: " + caloriesBurned + " Cal");

        // Log the data to verify
        Log.d("WorkoutData", "Saved workout data: " + workoutSet.toString());
        prefs.edit().putStringSet(Constants.WORKOUT_KEY, workoutSet).apply();

        // Navigate to Workout List
        Intent intent = new Intent(this, WorkoutListActivity.class);
        startActivity(intent);

        // Reset timer
        timeInMillis = 0L;
        textViewTimer.setText(R.string.timer_default);
        isRunning = false;
        buttonStartStop.setText(R.string.start);
    }

    private float calculateCalories(String workoutType, int totalMinutes) {
        float MET;
        switch (workoutType.toLowerCase()) {
            case "walking":
                MET = 3.5f;
                break;
            case "running":
                MET = 9.8f;
                break;
            case "cycling":
                MET = 11.5f;
                break;
            default:
                Toast.makeText(this, "Invalid workout type. Using default MET: 3.5", Toast.LENGTH_SHORT).show();
                MET = 3.5f;
        }
        return MET * 3.5f * 70 / 200 * totalMinutes;
    }
}
