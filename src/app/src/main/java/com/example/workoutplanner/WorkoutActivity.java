package com.example.workoutplanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import Entities.Exercise;
import Entities.Routine;
import Entities.RoutineExercise;

public class WorkoutActivity extends AppCompatActivity {

    private static final long START_TIME = 5000; // 5 seconds

    TextView tvCurrentExerciseName, tvCurrentRoutineSet, tvCurrentExerciseReps, tvTimerType, tvTimer;
    Button btnDone, btnStartPause;

    CountDownTimer timer;
    Routine currentRoutine;
    ArrayList<RoutineExercise> routineExercises;
    int currentSet, currentExercisePos, exerciseAmount;
    long timeLeft;
    boolean isTimerRunning, isTimeOffDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        tvCurrentExerciseName = findViewById(R.id.tvCurrentExerciseName);
        tvCurrentRoutineSet = findViewById(R.id.tvCurrentRoutineSet);
        tvCurrentExerciseReps = findViewById(R.id.tvCurrentExerciseReps);
        tvTimerType = findViewById(R.id.tvTimerType);
        tvTimer = findViewById(R.id.tvTimer);
        btnDone = findViewById(R.id.btnDone);
        btnStartPause = findViewById(R.id.btnStartPause);

        ActionBar actionBar = getSupportActionBar();
        if(getIntent().hasExtra(Constants.ROUTINE_EXTRA)) {
            currentRoutine = (Routine) getIntent().getSerializableExtra(Constants.ROUTINE_EXTRA);
            routineExercises = currentRoutine.getRoutineExercises();
            actionBar.setTitle(currentRoutine.getTitle());
        }

        currentSet = 1;
        currentExercisePos = 1;
        exerciseAmount = routineExercises.size();
        isTimeOffDone = true;
        timeLeft = START_TIME;
        NextExercise();

        btnDone.setOnClickListener(v -> StartTimer());

        btnStartPause.setOnClickListener(v -> {
            if (isTimerRunning) {
                PauseTimer();
            } else {
                StartTimer();
            }
        });
    }

    private void NextExercise(){
        if(currentSet <= currentRoutine.getSets()){
            if(currentExercisePos <= exerciseAmount){
                RoutineExercise currentRoutineExercise = routineExercises.get(currentExercisePos - 1);
                Exercise currentExercise = currentRoutineExercise.getExercise();
                tvCurrentExerciseName.setText(currentExercise.getName());
                tvCurrentRoutineSet.setText(currentSet + "/" + currentRoutine.getSets());

                // There is a repetitions based exercise
                if(currentRoutineExercise.getReps() != 0){
                    tvCurrentExerciseReps.setText(String.valueOf(currentRoutineExercise.getReps()));

                    tvTimerType.setText("Time Off"); // Add red color
                    tvTimer.setTextColor(getResources().getColor(R.color.red));
                    timeLeft = GetTimeLeftFromMinutes(currentRoutineExercise.getTimeOff());
                    UpdateTimerText();
                    currentExercisePos++;
                }
                else {
                    tvCurrentExerciseReps.setText("N/A");
                    if(isTimeOffDone == false){
                        if(currentRoutineExercise.getTimeOff() != 0){
                            tvTimerType.setText("Time Off"); // Add red color
                            tvTimer.setTextColor(getResources().getColor(R.color.red));
                            timeLeft = GetTimeLeftFromMinutes(currentRoutineExercise.getTimeOff());
                            UpdateTimerText();
                            StartTimer();
                            currentExercisePos++;
                        }
                        isTimeOffDone = true;
                    }
                    else {
                        if(currentRoutineExercise.getTimeOn() != 0){
                            tvTimerType.setText("Time On");
                            tvTimer.setTextColor(getResources().getColor(R.color.purple_200));
                            timeLeft = GetTimeLeftFromMinutes(currentRoutineExercise.getTimeOn());
                            UpdateTimerText();
                            isTimeOffDone = false;
                            return;
                        }
                    }
                }
            }
            else {
                // Go to next set
                //Set timer to set rest
                if(currentSet == currentRoutine.getSets()){
                    Utils.ToastMessage(this, "Workout done");
                    Dialog dialog = new Dialog(WorkoutActivity.this);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.congrat_dialog);
                    Button btnFinish = dialog.findViewById(R.id.btnFinish);
                    ImageView imgClose = dialog.findViewById(R.id.imgClose);
                    imgClose.setOnClickListener(v -> dialog.dismiss());
                    btnFinish.setOnClickListener(v -> {
                        dialog.dismiss();
                        Intent intent = new Intent(WorkoutActivity.this, MainActivity.class);
                        startActivity(intent);
                    });
                    dialog.show();
                }

                else{
                    currentSet++;
                    currentExercisePos = 1;
                    tvTimerType.setText("Set Rest");
                    tvTimer.setTextColor(getResources().getColor(R.color.purple_500));
                    timeLeft = GetTimeLeftFromMinutes(currentRoutine.getSetRest());
                    UpdateTimerText();
                    StartTimer();
                }
            }
        }
        else{
            // The workout is done
            Utils.ToastMessage(this, "Workout done 2. Should not get here");
        }
    }

    private long GetTimeLeftFromMinutes(float minutes){
        int seconds = (int)(minutes * 60);
        long result = seconds * 1000;
        return result;
    }

    private void StartTimer() {
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                UpdateTimerText();
            }

            @Override
            public void onFinish() {
                // Add a beeb
                final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_RING, 100);
                tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                NextExercise();
            }
        }.start();

        isTimerRunning = true;
        btnStartPause.setText("Pause");
    }

    private void PauseTimer() {
        timer.cancel();
        isTimerRunning = false;
        btnStartPause.setText("Start");
    }

    private void UpdateTimerText() {
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvTimer.setText(timeLeftFormatted);
    }
}