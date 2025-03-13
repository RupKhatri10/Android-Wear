package com.example.group2_midterm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import com.example.group2_midterm.adapter.WorkoutAdapter;
import com.example.group2_midterm.model.WorkoutSession;
import com.example.group2_midterm.utility.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkoutListActivity extends AppCompatActivity {
    private WorkoutAdapter adapter;
    private List<WorkoutSession> workoutList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        WearableRecyclerView recyclerView = findViewById(R.id.recyclerViewWorkouts);
        recyclerView.setEdgeItemsCenteringEnabled(true);
        recyclerView.setLayoutManager(new WearableLinearLayoutManager(this));

        workoutList = new ArrayList<>();
        adapter = new WorkoutAdapter(workoutList);
        recyclerView.setAdapter(adapter);

        loadWorkoutData();
    }

    private void loadWorkoutData() {
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> workoutSet = prefs.getStringSet(Constants.WORKOUT_KEY, new HashSet<>());
        workoutList.clear(); // Clear existing data to prevent duplicates
        for (String workout : workoutSet) {
            String[] details = workout.split(" - | Calories Burned: ");
            if (details.length == 3) {
                try {
                    String workoutType = details[0].trim();
                    int duration = Integer.parseInt(details[1].replace(" minutes", "").trim());
                    float caloriesBurned = Float.parseFloat(details[2].replace(" Cal", "").trim());
                    workoutList.add(new WorkoutSession(workoutType, duration, caloriesBurned));
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Handle any potential number parsing errors
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
