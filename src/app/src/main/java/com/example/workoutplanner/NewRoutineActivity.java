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
import DataAccess.RoutineRepository;
import Entities.Exercise;
import Entities.Routine;
import Entities.RoutineExercise;

public class NewRoutineActivity extends AppCompatActivity  implements RoutineExerciseEditDialog.RoutineExerciseDialogListener{

    EditText inputRoutineTitle, inputRoutineDuration, inputRoutineDay, inputRoutineSetRest, inputRoutineSets;
    Button btnAddRoutine, btnAddRoutineExercise;
    RecyclerView rvRoutineExercisesPreview;

    ArrayList<Exercise> exercises;
    ArrayList<RoutineExercise> routineExercises = new ArrayList<>();

    ExerciseRepository exerciseRepository;
    RoutineExerciseAdapter routineExerciseAdapter;
    Dialog dialog;

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
        btnAddRoutine.setOnClickListener(v -> ComposeRoutine());
        btnAddRoutineExercise.setFocusableInTouchMode(true);
        btnAddRoutineExercise.requestFocus();
    }

    private void ComposeRoutine(){
        if(RoutineValidations()){
            String title = inputRoutineTitle.getText().toString();
            String day = inputRoutineDay.getText().toString();
            float duration =  Float.parseFloat(inputRoutineDuration.getText().toString());
            float setRest =  Float.parseFloat(inputRoutineSetRest.getText().toString());
            int sets =  Integer.parseInt(inputRoutineSets.getText().toString());
            Routine routine = new Routine(title, day, sets, setRest, duration, routineExercises);

            RoutineRepository routineRepository = new RoutineRepository(new DatabaseManager(this));
            routineRepository.AddRoutine(routine);
            CleanInputs();
            recreate();
        }
    }

    private void CleanInputs(){
        inputRoutineTitle.setText("");
        inputRoutineDuration.setText("");
        inputRoutineSets.setText("");
        inputRoutineSetRest.setText("");
        inputRoutineDay.setText("");
        routineExercises = new ArrayList<>();
    }

    private boolean RoutineValidations(){
        boolean areRequired = Utils.isAnyTextEmpty(inputRoutineTitle, inputRoutineDuration, inputRoutineDay, inputRoutineSetRest, inputRoutineSetRest);
        if(areRequired){
            Utils.ToastMessage(this, "Complete all inputs");
            return false;
        }
        int exercisesCount = routineExercises.size();
        if(exercisesCount < 1){
            Utils.ToastMessage(this, "You need to add at least 1 exercise");
            return false;
        }
        for (RoutineExercise item: routineExercises) {
            if(
                item.getReps() == 0 ){
                Utils.ToastMessage(this, "Make sure all exercises are set up");
                return false;
            }
        }
        return true;
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
    @Override
    public void RemoveRoutineExercise(int position) {
        routineExercises.remove(position);
        int arraySize = routineExercises.size();
        for (int i = 0; i < arraySize; i++ ){
            routineExercises.get(i).setOrderNumber(i + 1);
        }
        LoadRoutineExercises();
    }

    @Override
    public void SaveRoutineExercise(RoutineExercise routineExercise) {
        LoadRoutineExercises();
    }

    public void LoadRoutineExercises(){
        routineExerciseAdapter = new RoutineExerciseAdapter(NewRoutineActivity.this, this, routineExercises, getSupportFragmentManager(), true);
        rvRoutineExercisesPreview.setAdapter(routineExerciseAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewRoutineActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        rvRoutineExercisesPreview.setLayoutManager(linearLayoutManager);
    }
}