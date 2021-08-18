package com.example.workoutplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import CustomAdapters.ExerciseAdapter;
import DataAccess.DatabaseManager;
import DataAccess.ExerciseRepository;

public class ExercisesActivity extends AppCompatActivity {

    FloatingActionButton btnGoToAddExercise;
    ExerciseAdapter exerciseAdapter;
    RecyclerView rvExercises;
    ExerciseRepository exerciseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        rvExercises = findViewById(R.id.rvExercises);
        btnGoToAddExercise = findViewById(R.id.btnGoToAddExercise);
        btnGoToAddExercise.setOnClickListener(v -> {
            Intent intent = new Intent(ExercisesActivity.this, NewExerciseActivity.class);
            startActivity(intent);
        });

        exerciseRepository = new ExerciseRepository(new DatabaseManager(this));
        exerciseAdapter = new ExerciseAdapter(ExercisesActivity.this, this, exerciseRepository.GetAllExercises());
        rvExercises.setAdapter(exerciseAdapter);
        rvExercises.setLayoutManager(new LinearLayoutManager(ExercisesActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
}