package com.example.workoutplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import DataAccess.DatabaseManager;
import DataAccess.ExerciseRepository;
import Entities.Exercise;

public class NewExerciseActivity extends AppCompatActivity {

    EditText inputExerciseName;
    EditText inputExerciseTargetMuscles;
    EditText inputExerciseReferenceLink;
    Button btnAddExercises;
    ExerciseRepository exerciseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        exerciseRepository = new ExerciseRepository(new DatabaseManager(NewExerciseActivity.this));

        inputExerciseName = findViewById(R.id.inputExerciseName);
        inputExerciseTargetMuscles = findViewById(R.id.inputExerciseTargetMuscles);
        inputExerciseReferenceLink = findViewById(R.id.inputExerciseReferenceLink);
        btnAddExercises = findViewById(R.id.btnAddExercises);

        btnAddExercises.setOnClickListener(v -> AddExercise());
    }

    private void AddExercise(){
       boolean areAnyRequiredInputsEmpty = Utils.isAnyTextEmpty(inputExerciseName, inputExerciseTargetMuscles);
        if(areAnyRequiredInputsEmpty == true){
            Utils.ToastMessage(this, "The exercise name and target muscles are required");
        }
        else {
            String name = inputExerciseName.getText().toString().trim();
            String targetMuscles = inputExerciseTargetMuscles.getText().toString().trim();
            String refLink = inputExerciseReferenceLink.getText().toString().trim();
            Exercise exercise = new Exercise(name, targetMuscles, refLink);
            exerciseRepository.AddExercise(exercise);
            ClearInputs();
        }
    }

    private void ClearInputs(){
        inputExerciseName.setText("");
        inputExerciseTargetMuscles.setText("");
        inputExerciseReferenceLink.setText("");
    }
}