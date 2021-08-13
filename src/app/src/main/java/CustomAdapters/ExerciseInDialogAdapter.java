package CustomAdapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutplanner.R;
import com.example.workoutplanner.Utils;

import java.util.ArrayList;

import Entities.Exercise;
import Entities.RoutineExercise;

public class ExerciseInDialogAdapter extends RecyclerView.Adapter<ExerciseInDialogAdapter.ViewHolder>{

    Context context;
    Activity activity;
    ArrayList<Exercise> exercises;
    ArrayList<RoutineExercise> routineExercises;
    Dialog dialog;

    public ExerciseInDialogAdapter(Activity activity, Context context, ArrayList<Exercise> exercises, ArrayList<RoutineExercise> routineExercises, Dialog dialog){
        this.activity = activity;
        this.context = context;
        this.exercises = exercises;
        this.routineExercises = routineExercises;
        this.dialog = dialog;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exercise_dialog_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ExerciseInDialogAdapter.ViewHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        holder.tvExerciseNameInDialog.setText( String.valueOf(currentExercise.getName()));
        holder.tvTargetMusclesInDialog.setText(String.valueOf(currentExercise.getTargetMuscles()));

        holder.exerciseDialogListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routineExercises.add(0, new RoutineExercise(currentExercise));
                dialog.dismiss();
                Utils.ToastMessage(context, "You selected " + currentExercise.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTargetMusclesInDialog, tvExerciseNameInDialog;
        LinearLayout exerciseDialogListLayout;
        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            tvTargetMusclesInDialog = itemView.findViewById(R.id.tvRoutineExerciseReps);
            tvExerciseNameInDialog = itemView.findViewById(R.id.tvExerciseNameInDialog);
            exerciseDialogListLayout = itemView.findViewById(R.id.exerciseDialogListLayout);
        }
    }
}
