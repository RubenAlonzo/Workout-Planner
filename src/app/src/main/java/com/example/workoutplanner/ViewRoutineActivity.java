package com.example.workoutplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import CustomAdapters.RoutineExerciseAdapter;
import DataAccess.DatabaseManager;
import DataAccess.RoutineRepository;
import Entities.Exercise;
import Entities.Routine;
import Entities.RoutineExercise;

public class ViewRoutineActivity extends AppCompatActivity implements RoutineExerciseEditDialog.RoutineExerciseDialogListener {

    Routine currentRoutine;
    TextView tvSelectedRoutineDay, tvSelectedRoutineSets,tvSelectedRoutineSetRest, tvSelectedRoutineDuration;
    Button btnStartRoutine;
    RecyclerView rvSelectedRoutineExercises;

    RoutineRepository routineRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_routine);

        ActionBar actionBar = getSupportActionBar();
        tvSelectedRoutineDay = findViewById(R.id.tvSelectedRoutineDay);
        tvSelectedRoutineSets = findViewById(R.id.tvSelectedRoutineSets);
        tvSelectedRoutineSetRest = findViewById(R.id.tvSelectedRoutineSetRest);
        tvSelectedRoutineDuration = findViewById(R.id.tvSelectedRoutineDuration);
        btnStartRoutine = findViewById(R.id.btnStartRoutine);
        rvSelectedRoutineExercises = findViewById(R.id.rvSelectedRoutineExercises);

        if(getIntent().hasExtra(Constants.ROUTINE_EXTRA)){
            currentRoutine = (Routine) getIntent().getSerializableExtra(Constants.ROUTINE_EXTRA);
            actionBar.setTitle(currentRoutine.getTitle());
            tvSelectedRoutineDay.setText(currentRoutine.getDay());
            tvSelectedRoutineSets.setText(String.valueOf(currentRoutine.getSets()));
            tvSelectedRoutineSetRest.setText(Utils.ConvertDecimalsToMinutes(currentRoutine.getSetRest()));
            tvSelectedRoutineDuration.setText(Utils.ConvertDecimalsToMinutes(currentRoutine.getEstimatedDuration()));
        }
        routineRepository = new RoutineRepository(new DatabaseManager(this));
        LoadRoutineExercises();
    }

    private void ConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this routine?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            routineRepository.DeleteRoutine(currentRoutine.getId());
            finish();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> { });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_routine_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.deleteRoutine){
            ConfirmationDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadRoutineExercises(){
        RoutineExerciseAdapter routineExerciseAdapter = new RoutineExerciseAdapter(ViewRoutineActivity.this, this, currentRoutine.getRoutineExercises(), getSupportFragmentManager(), false);
        rvSelectedRoutineExercises.setAdapter(routineExerciseAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewRoutineActivity.this);
        rvSelectedRoutineExercises.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void RemoveRoutineExercise(int position) { }

    @Override
    public void SaveRoutineExercise(RoutineExercise routineExercise) { }
}