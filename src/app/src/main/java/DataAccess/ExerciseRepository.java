package DataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.example.workoutplanner.Utils;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import Entities.Exercise;

public class ExerciseRepository {
    private DatabaseManager dbMgr;

    public ExerciseRepository(DatabaseManager dbManager) {
        this.dbMgr = dbManager;
    }

    public void AddExercise(@NotNull Exercise exercise) {
        try {
            SQLiteDatabase db = dbMgr.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbMgr.EXERCISE_NAME, exercise.getName());
            contentValues.put(dbMgr.EXERCISE_TARGET_MUSCLE, exercise.getTargetMuscles());
            contentValues.put(dbMgr.EXERCISE_LINK, exercise.getRefLink());
            long result = db.insert(dbMgr.TABLE_EXERCISE, null, contentValues);
            Utils.ValidateDbResult(dbMgr.context, result, "Exercise added successfully!");
        }
        catch (SQLiteException e){
            Utils.ToastMessage(dbMgr.context, e.getMessage());
        }
    }

    public ArrayList<Exercise> GetAllExercises() {
        ArrayList<Exercise> exercises = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + dbMgr.TABLE_EXERCISE + " ORDER BY " + dbMgr.KEY_ID + " DESC";
            SQLiteDatabase db = dbMgr.getReadableDatabase();
            if (db != null) {
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex(dbMgr.KEY_ID));
                        String name = cursor.getString(cursor.getColumnIndex(dbMgr.EXERCISE_NAME));
                        String targetMuscle = cursor.getString(cursor.getColumnIndex(dbMgr.EXERCISE_TARGET_MUSCLE));
                        String refLink = cursor.getString(cursor.getColumnIndex(dbMgr.EXERCISE_LINK));
                        exercises.add(new Exercise(id, name, targetMuscle, refLink));
                    }
                }
            }
        }
        catch (SQLiteException e){
            Utils.ToastMessage(dbMgr.context, e.getMessage());
        }
        return exercises;
    }

    public void UpdateExercise(Exercise exercise) {
        try {
            SQLiteDatabase db = dbMgr.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbMgr.EXERCISE_NAME, exercise.getName());
            contentValues.put(dbMgr.EXERCISE_TARGET_MUSCLE, exercise.getTargetMuscles());
            contentValues.put(dbMgr.EXERCISE_LINK, exercise.getRefLink());

            long result = db.update(dbMgr.TABLE_EXERCISE, contentValues, dbMgr.KEY_ID + "=?", new String[]{String.valueOf(exercise.getId())});
            Utils.ValidateDbResult(dbMgr.context, result, "Exercise updated successfully!");
        }
        catch (SQLiteException e){
            Utils.ToastMessage(dbMgr.context, e.getMessage());
        }
    }

    public void DeleteExercise(Exercise exercise) {
        try {
            SQLiteDatabase db = dbMgr.getWritableDatabase();
            long result = db.delete(dbMgr.TABLE_EXERCISE, dbMgr.KEY_ID + "=?", new String[]{String.valueOf(exercise.getId())});
            Utils.ValidateDbResult(dbMgr.context, result, "Exercise deleted successfully!");
        }
        catch (SQLiteException e){
            Utils.ToastMessage(dbMgr.context, e.getMessage());
        }
    }
}
