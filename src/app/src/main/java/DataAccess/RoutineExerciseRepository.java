package DataAccess;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.workoutplanner.Utils;

import java.util.ArrayList;

import Entities.Exercise;
import Entities.RoutineExercise;

public class RoutineExerciseRepository {

    private DatabaseManager dbMgr;
    private ExerciseRepository exerciseRepository;

    public RoutineExerciseRepository(DatabaseManager dbManager) {
        this.dbMgr = dbManager;
        exerciseRepository = new ExerciseRepository(dbManager);
    }

    public void AddRoutineExercise(RoutineExercise routineExercise) {
    }


    public ArrayList<RoutineExercise> GetAllRoutineExercisesByRoutineId(int routineId) {
        ArrayList<RoutineExercise> routineExerciseList = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + dbMgr.TABLE_ROUTINE_EXERCISE + " WHERE " + dbMgr.KEY_ROUTINE_ID + " = " + routineId;
            SQLiteDatabase db = dbMgr.getReadableDatabase();
            if (db != null) {
                Cursor innerCursor = db.rawQuery(query, null);
                if (innerCursor.getCount() != 0) {
                    while (innerCursor.moveToNext()){
                        int idRoutineExercise = innerCursor.getInt(innerCursor.getColumnIndex(dbMgr.KEY_ID));
                        int idRoutine = innerCursor.getInt(innerCursor.getColumnIndex(dbMgr.KEY_ROUTINE_ID));
                        int idExercise = innerCursor.getInt(innerCursor.getColumnIndex(dbMgr.KEY_EXERCISE_ID));
                        int orderNumber = innerCursor.getInt(innerCursor.getColumnIndex(dbMgr.ROUTINE_EXERCISE_ORDER_NUMBER));
                        int reps = innerCursor.getInt(innerCursor.getColumnIndex(dbMgr.ROUTINE_EXERCISE_REPS));
                        float timeOn = innerCursor.getFloat(innerCursor.getColumnIndex(dbMgr.ROUTINE_EXERCISE_TIME_ON));
                        float timeOff = innerCursor.getFloat(innerCursor.getColumnIndex(dbMgr.ROUTINE_EXERCISE_TIME_OFF));

                        RoutineExercise routineExercise = new RoutineExercise(idRoutineExercise, idRoutine, idExercise, orderNumber, reps, timeOn, timeOff);
                        routineExercise.setExercise(exerciseRepository.GetExerciseById(idExercise)); //Possible NULL reference
                        routineExerciseList.add(routineExercise);
                    }
                }
            }
        }
        catch (SQLiteException e){
            Utils.ToastMessage(dbMgr.context, e.getMessage());
        }
        return routineExerciseList;
    }
}
