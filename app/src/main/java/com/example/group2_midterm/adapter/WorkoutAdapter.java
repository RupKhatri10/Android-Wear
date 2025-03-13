package com.example.group2_midterm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group2_midterm.R;
import com.example.group2_midterm.model.WorkoutSession;

import java.util.List;
import java.util.Locale;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private final List<WorkoutSession> workoutSessionList;

    public WorkoutAdapter(List<WorkoutSession> workoutSessionList) {
        this.workoutSessionList = workoutSessionList;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        WorkoutSession session = workoutSessionList.get(position);
        String workoutDetails = String.format(Locale.getDefault(),
                "%s - %d minutes Calories Burned: %.0f Cal.",
                session.getWorkoutType(),
                session.getDuration(),
                session.getCaloriesBurned());
        holder.textViewWorkoutItem.setText(workoutDetails);
    }

    @Override
    public int getItemCount() {
        return workoutSessionList.size();
    }

    static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView textViewWorkoutItem;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWorkoutItem = itemView.findViewById(R.id.textViewWorkoutItem);
        }
    }
}