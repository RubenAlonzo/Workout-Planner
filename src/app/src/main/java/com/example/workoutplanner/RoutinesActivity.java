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
    ArrayList<Routine> FakeRoutines = new ArrayList<>();

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

        FakeRoutines.add(new Routine(1, "Push Thenx", "Monday", "Chest and Shoulders", 3, 2, 30));
        FakeRoutines.add(new Routine(2, "Pull Athlean", "Wednesday", "Back and Biceps", 5, 2, 45));
        FakeRoutines.add(new Routine(3, "Legs Thenx", "Friday", "Legs", 5, 2, 60));

        routineRepository = new RoutineRepository(new DatabaseManager(this));
        routineAdapter = new RoutineAdapter(RoutinesActivity.this, this, FakeRoutines);
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