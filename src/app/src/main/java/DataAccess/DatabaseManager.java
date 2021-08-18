package DataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WorkoutPlanner.db";
    private static final int DATABASE_VERSION = 1;
    public Context context;

    public static final String KEY_ID = "id";
    public static final String KEY_EXERCISE_ID = "id_exercise";
    public static final String KEY_ROUTINE_ID = "id_routine";

    public static final String TABLE_EXERCISE = "Exercise";
    public static final String EXERCISE_NAME = "name";
    public static final String EXERCISE_TARGET_MUSCLE = "target_muscle";
    public static final String EXERCISE_LINK = "link";
    public static final String CREATE_TABLE_EXERCISE_QUERY = "CREATE TABLE " + TABLE_EXERCISE +
            " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ EXERCISE_NAME + " TEXT NOT NULL, " + EXERCISE_TARGET_MUSCLE + " TEXT NOT NULL, " + EXERCISE_LINK + " TEXT );";

    public static final String TABLE_ROUTINE = "Routine";
    public static final String ROUTINE_TITLE = "title";
    public static final String ROUTINE_TARGET_MUSCLE = "target_muscle";
    public static final String ROUTINE_DAY = "day";
    public static final String ROUTINE_SETS = "sets";
    public static final String ROUTINE_SETS_REST = "set_rest";
    public static final String ROUTINE_ESTIMATED_DURATION = "estimated_duration";
    public static final String CREATE_TABLE_ROUTINE_QUERY = "CREATE TABLE " + TABLE_ROUTINE +
            " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ ROUTINE_TITLE + " TEXT NOT NULL, " + ROUTINE_TARGET_MUSCLE + " TEXT, " + ROUTINE_DAY + " TEXT, " +
            ROUTINE_SETS + " INTEGER NOT NULL, " + ROUTINE_SETS_REST + " REAL NOT NULL, " + ROUTINE_ESTIMATED_DURATION + " REAL );";

    public static final String TABLE_ROUTINE_EXERCISE = "RoutineExercise";
    public static final String ROUTINE_EXERCISE_ORDER_NUMBER = "order_number";
    public static final String ROUTINE_EXERCISE_REPS = "reps";
    public static final String ROUTINE_EXERCISE_TIME_ON = "time_on";
    public static final String ROUTINE_EXERCISE_TIME_OFF = "time_off";
    public static final String CREATE_TABLE_ROUTINE_EXERCISE_QUERY = "CREATE TABLE " + TABLE_ROUTINE_EXERCISE +
            " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ROUTINE_ID + " INTEGER NOT NULL, " + KEY_EXERCISE_ID + " INTEGER NOT NULL, " + ROUTINE_EXERCISE_ORDER_NUMBER + " INTEGER NOT NULL, " + ROUTINE_EXERCISE_REPS + " INTEGER NOT NULL, " +
            ROUTINE_EXERCISE_TIME_ON + " REAL, " + ROUTINE_EXERCISE_TIME_OFF + " REAL NOT NULL, " +
            "FOREIGN KEY ( " + KEY_EXERCISE_ID + " ) REFERENCES " + TABLE_EXERCISE + "( " + KEY_ID + " ), " +
            "FOREIGN KEY ( " + KEY_ROUTINE_ID + " ) REFERENCES " + TABLE_ROUTINE + "( " + KEY_ID + " )"  + " );";

    public DatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXERCISE_QUERY);
        db.execSQL(CREATE_TABLE_ROUTINE_QUERY);
        db.execSQL(CREATE_TABLE_ROUTINE_EXERCISE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTINE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTINE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        onCreate(db);
    }
}