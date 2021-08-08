package com.example.workoutplanner;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.lang.reflect.Array;

public class Utils {

    public static boolean isAnyTextEmpty(EditText... texts ){
        for (EditText item: texts) {
            if(item.getText().toString().trim().isEmpty()){
                return true;
            }
        }
        return false;
    }

    public static void ToastMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void ValidateDbResult(Context context, long result, String successMessage, String failedMessage){
        if (result == -1) {
            Toast.makeText(context, failedMessage, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public static void ValidateDbResult(Context context, long result, String successMessage){
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
