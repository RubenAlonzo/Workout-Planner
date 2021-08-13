package com.example.workoutplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import CustomAdapters.ExerciseAdapter;
import CustomAdapters.ExerciseInDialogAdapter;
import CustomAdapters.RoutineExerciseAdapter;
import DataAccess.DatabaseManager;
import DataAccess.ExerciseRepository;
import Entities.Exercise;
import Entities.RoutineExercise;

public class NewRoutineActivity extends AppCompatActivity {

    EditText inputRoutineTitle, inputRoutineDuration, inputRoutineDay, inputRoutineSetRest, inputRoutineSets;
    Button btnAddRoutine, btnAddRoutineExercise;
    RecyclerView rvRoutineExercisesPreview;

    ExerciseRepository exerciseRepository;
    ArrayList<Exercise> exercises;
    ArrayList<RoutineExercise> routineExercises = new ArrayList<>();
    Dialog dialog;
    RoutineExerciseAdapter routineExerciseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_routine);

        inputRoutineTitle = findViewById(R.id.inputRoutineTitle);
        inputRoutineDuration = findViewById(R.id.inputRoutineDuration);
        inputRoutineDay = findViewById(R.id.inputRoutineDay);
        inputRoutineSetRest = findViewById(R.id.inputRoutineSetRest);
        inputRoutineSets = findViewById(R.id.inputRoutineSets);
        btnAddRoutine = findViewById(R.id.btnAddRoutine);
        btnAddRoutineExercise = findViewById(R.id.btnAddRoutineExercise);
        rvRoutineExercisesPreview = findViewById(R.id.rvRoutineExercisesPreview);

        exerciseRepository = new ExerciseRepository(new DatabaseManager(this));
        exercises = exerciseRepository.GetAllExercises();

        btnAddRoutineExercise.setOnClickListener(v -> showDialog(NewRoutineActivity.this));
    }

    public void showDialog(Activity activity){
        dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_recycler);

        Button btnCancelDialog = (Button) dialog.findViewById(R.id.btnCancelDialog);
        btnCancelDialog.setOnClickListener(v -> dialog.dismiss());

        RecyclerView recyclerView = dialog.findViewById(R.id.rvDialogPickExercises);
        ExerciseInDialogAdapter adapterRe = new ExerciseInDialogAdapter(NewRoutineActivity.this, this, exercises, routineExercises, dialog);
        recyclerView.setAdapter(adapterRe);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                LoadRoutineExercises();
            }
        });

        dialog.show();
    }

    public void LoadRoutineExercises(){
        routineExerciseAdapter = new RoutineExerciseAdapter(NewRoutineActivity.this, this, routineExercises);
        rvRoutineExercisesPreview.setAdapter(routineExerciseAdapter);
        rvRoutineExercisesPreview.setLayoutManager(new LinearLayoutManager(NewRoutineActivity.this));
    }
}