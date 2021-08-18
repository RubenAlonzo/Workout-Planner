package com.example.workoutplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import DataAccess.DatabaseManager;
import DataAccess.ExerciseRepository;
import Entities.Exercise;

public class AlterExerciseActivity extends AppCompatActivity {

    EditText inputEditExerciseName;
    EditText inputEditExerciseTargetMuscles;
    EditText inputEditExerciseReferenceLink;
    Button btnUpdateExercise;
    Button btnDeleteExercise;
    ExerciseRepository exerciseRepository;
    Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_exercise);

        exerciseRepository = new ExerciseRepository(new DatabaseManager(this));

        inputEditExerciseName = findViewById(R.id.inputEditExerciseName);
        inputEditExerciseTargetMuscles = findViewById(R.id.inputEditExerciseTargetMuscles);
        inputEditExerciseReferenceLink = findViewById(R.id.inputEditExerciseReferenceLink);
        btnUpdateExercise = findViewById(R.id.btnUpdateExercise);
        btnDeleteExercise = findViewById(R.id.btnDeleteExercise);

        if(getIntent().hasExtra(Constants.EXERCISE_EXTRA)){
            exercise = (Exercise) getIntent().getSerializableExtra(Constants.EXERCISE_EXTRA);
            inputEditExerciseName.setText(exercise.getName());
            inputEditExerciseTargetMuscles.setText(exercise.getTargetMuscles());
            inputEditExerciseReferenceLink.setText(exercise.getRefLink());
        }

        btnUpdateExercise.setOnClickListener(v -> UpdateExercise());
        btnDeleteExercise.setOnClickListener(v -> ConfirmationDialog());
    }

    private void UpdateExercise(){
        boolean isAnyEmpty = Utils.isAnyTextEmpty(inputEditExerciseName, inputEditExerciseTargetMuscles);
        if(isAnyEmpty){
            Utils.ToastMessage(this, "The exercise name and target muscles are required");
        }
        else {
            String name = inputEditExerciseName.getText().toString().trim();
            String targetMuscles = inputEditExerciseTargetMuscles.getText().toString().trim();
            String refLink = inputEditExerciseReferenceLink.getText().toString().trim();
            exercise.setName(name);
            exercise.setTargetMuscles(targetMuscles);
            exercise.setRefLink(refLink);
            exerciseRepository.UpdateExercise(exercise);
            finish();
        }
    }

    private void ConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this exercise?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            exerciseRepository.DeleteExercise(exercise);
            finish();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> { });
        builder.create().show();
    }
}