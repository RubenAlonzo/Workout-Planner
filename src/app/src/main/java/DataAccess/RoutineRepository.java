package DataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.example.workoutplanner.Utils;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

import Entities.Routine;
import Entities.RoutineExercise;

public class RoutineRepository {
    private DatabaseManager dbMgr;
    private RoutineExerciseRepository routineExerciseRepository;
    private ExerciseRepository exerciseRepository;

    public RoutineRepository(DatabaseManager dbManager) {
        this.dbMgr = dbManager;
        routineExerciseRepository = new RoutineExerciseRepository(dbManager);
        exerciseRepository = new ExerciseRepository(dbManager);
    }

    public void AddRoutine(@NotNull Routine routine) {
        SQLiteDatabase db = dbMgr.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbMgr.ROUTINE_TITLE, routine.getTitle());
            contentValues.put(dbMgr.ROUTINE_DAY, routine.getDay());
            contentValues.put(dbMgr.ROUTINE_TARGET_MUSCLE, routine.getTargetMuscles());
            contentValues.put(dbMgr.ROUTINE_SETS, routine.getSets());
            contentValues.put(dbMgr.ROUTINE_SETS_REST, routine.getSetRest());
            contentValues.put(dbMgr.ROUTINE_ESTIMATED_DURATION, routine.getEstimatedDuration());

            long routineId = db.insert(dbMgr.TABLE_ROUTINE, null, contentValues);

            if(routineId != -1){
                ContentValues values;
                for (RoutineExercise routineExercise: routine.getRoutineExercises()) {
                    routineExercise.setIdRoutine((int)routineId);
                    values = new ContentValues();
                    values.put(dbMgr.KEY_ROUTINE_ID, routineExercise.getIdRoutine());
                    values.put(dbMgr.KEY_EXERCISE_ID, routineExercise.getIdExercise());
                    values.put(dbMgr.ROUTINE_EXERCISE_ORDER_NUMBER, routineExercise.getOrderNumber());
                    values.put(dbMgr.ROUTINE_EXERCISE_REPS, routineExercise.getReps());
                    values.put(dbMgr.ROUTINE_EXERCISE_TIME_ON, routineExercise.getTimeOn());
                    values.put(dbMgr.ROUTINE_EXERCISE_TIME_OFF, routineExercise.getTimeOff());

                    long result = db.insert(dbMgr.TABLE_ROUTINE_EXERCISE, null, values);
                }

                db.setTransactionSuccessful();
            }
        }
        catch (SQLiteException e){
            Utils.ToastMessage(dbMgr.context, e.getMessage());
        }
        finally {
            db.endTransaction();
        }
    }

    public ArrayList<Routine> GetAllRoutines() {
        ArrayList<Routine> routineList = new ArrayList<>();

        try {
            String query = "SELECT * FROM " + dbMgr.TABLE_ROUTINE + " ORDER BY " + dbMgr.ROUTINE_DAY + " DESC";
            SQLiteDatabase db = dbMgr.getReadableDatabase();
            if (db != null) {
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex(dbMgr.KEY_ID));
                        String title = cursor.getString(cursor.getColumnIndex(dbMgr.ROUTINE_TITLE));
                        String day = cursor.getString(cursor.getColumnIndex(dbMgr.ROUTINE_DAY));
                        String targetMuscle = cursor.getString(cursor.getColumnIndex(dbMgr.ROUTINE_TARGET_MUSCLE));
                        int sets = cursor.getInt(cursor.getColumnIndex(dbMgr.ROUTINE_SETS));
                        float setsRest = cursor.getFloat(cursor.getColumnIndex(dbMgr.ROUTINE_SETS_REST));
                        float estimatedDuration = cursor.getFloat(cursor.getColumnIndex(dbMgr.ROUTINE_ESTIMATED_DURATION));

                        Routine routine = new Routine(id, title, day, targetMuscle, sets, setsRest, estimatedDuration);
                        routine.setRoutineExercises(routineExerciseRepository.GetAllRoutineExercisesByRoutineId(id));
                        routineList.add(routine);
                    }
                }
            }
        }
        catch (SQLiteException e){
            Utils.ToastMessage(dbMgr.context, e.getMessage());
        }
        return routineList;
    }

    public void DeleteRoutine(int routineId) {
        SQLiteDatabase db = dbMgr.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(dbMgr.TABLE_ROUTINE_EXERCISE, dbMgr.KEY_ROUTINE_ID + "=?", new String[]{String.valueOf(routineId)});
            db.delete(dbMgr.TABLE_ROUTINE, dbMgr.KEY_ID + "=?", new String[]{String.valueOf(routineId)});
            db.setTransactionSuccessful();
        }
        catch (SQLiteException e){
            Utils.ToastMessage(dbMgr.context, e.getMessage());
        }
        finally {
            db.endTransaction();
        }
    }

    public void UpdateRoutine(Routine routine, int routineId) {
        DeleteRoutine(routineId);
        AddRoutine(routine);
    }
}
