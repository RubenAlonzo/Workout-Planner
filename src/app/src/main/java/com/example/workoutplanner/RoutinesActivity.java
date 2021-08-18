package com.example.workoutplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import CustomAdapters.ExerciseAdapter;
import CustomAdapters.RoutineAdapter;
import DataAccess.DatabaseManager;
import DataAccess.ExerciseRepository;
import DataAccess.RoutineRepository;
import Entities.Routine;

public class RoutinesActivity extends AppCompatActivity {

    FloatingActionButton btnGoToAddRoutine;
    RoutineAdapter routineAdapter;
    RecyclerView rvRoutines;
    RoutineRepository routineRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routines);

        rvRoutines = findViewById(R.id.rvRoutines);
        btnGoToAddRoutine = findViewById(R.id.btnGoToAddRoutine);
        btnGoToAddRoutine.setOnClickListener(v -> {
            Intent intent = new Intent(RoutinesActivity.this, NewRoutineActivity.class);
            startActivity(intent);
        });

        routineRepository = new RoutineRepository(new DatabaseManager(this));
        routineAdapter = new RoutineAdapter(RoutinesActivity.this, this, routineRepository.GetAllRoutines());
        rvRoutines.setAdapter(routineAdapter);
        rvRoutines.setLayoutManager(new LinearLayoutManager(RoutinesActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
}