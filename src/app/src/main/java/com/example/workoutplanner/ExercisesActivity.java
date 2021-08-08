package com.example.workoutplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExercisesActivity extends AppCompatActivity {

    FloatingActionButton btnGoToAddExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        btnGoToAddExercise = findViewById(R.id.btnGoToAddExercise);
        btnGoToAddExercise.setOnClickListener(v -> {
            Intent intent = new Intent(ExercisesActivity.this, NewExerciseActivity.class);
            startActivity(intent);
        });
    }
}