package com.example.group2_midterm.model;

public class WorkoutSession {
    private final String workoutType;
    private final int duration;
    private final float caloriesBurned;

    public WorkoutSession(String workoutType, int duration, float caloriesBurned) {
        this.workoutType = workoutType;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public int getDuration() {
        return duration;
    }

    public float getCaloriesBurned() {
        return caloriesBurned;
    }
}
