package DataAccess;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.workoutplanner.Utils;
import org.jetbrains.annotations.NotNull;
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
}
