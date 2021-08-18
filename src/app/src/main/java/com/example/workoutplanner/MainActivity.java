package com.example.workoutplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnExercises;
    Button btnRoutines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExercises = findViewById(R.id.btnExercises);
        btnRoutines = findViewById(R.id.btnRoutines);

        btnExercises.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExercisesActivity.class);
            startActivity(intent);
        });

        btnRoutines.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RoutinesActivity.class);
            startActivity(intent);
        });
    }
}