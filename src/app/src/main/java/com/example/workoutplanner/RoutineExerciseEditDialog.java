package com.example.workoutplanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import Entities.RoutineExercise;

public class RoutineExerciseEditDialog extends AppCompatDialogFragment {

    private RoutineExerciseDialogListener listener;
    private RoutineExercise routineExercise;
    private int position;

    private EditText inputDialogOrderNo, inputDialogReps, inputDialogTimeOn, inputDialogTimeOff;

    public RoutineExerciseEditDialog(RoutineExercise routineExercise, int position) {
        this.routineExercise = routineExercise;
        this.position = position;
    }

    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.routine_exercise_edit_dialog, null);

        inputDialogOrderNo = view.findViewById(R.id.inputDialogOrderNo);
        inputDialogReps = view.findViewById(R.id.inputDialogReps);
        inputDialogTimeOn = view.findViewById(R.id.inputDialogTimeOn);
        inputDialogTimeOff = view.findViewById(R.id.inputDialogTimeOff);

        SetTextValues();

        builder.setView(view)
                .setTitle(routineExercise.getExercise().getName())
                .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.RemoveRoutineExercise(position);
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GetValuesFromInputs();
                        listener.SaveRoutineExercise(routineExercise);
                    }
                });

        return builder.create();
    }

    private void SetTextValues(){
        inputDialogOrderNo.setText((String.valueOf(routineExercise.getOrderNumber())));
        inputDialogReps.setText(String.valueOf(routineExercise.getReps()));
        inputDialogTimeOn.setText(String.valueOf(routineExercise.getTimeOn()));
        inputDialogTimeOff.setText(String.valueOf(routineExercise.getTimeOff()));
    }

    private void GetValuesFromInputs(){
        routineExercise.setOrderNumber(Integer.parseInt(inputDialogOrderNo.getText().toString()));
        routineExercise.setReps(Integer.parseInt(inputDialogReps.getText().toString()));
        routineExercise.setTimeOn(Float.parseFloat(inputDialogTimeOn.getText().toString()));
        routineExercise.setTimeOff(Float.parseFloat(inputDialogTimeOff.getText().toString()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (RoutineExerciseDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface RoutineExerciseDialogListener {
        void RemoveRoutineExercise(int position);
        void SaveRoutineExercise(RoutineExercise routineExercise);
    }
}
