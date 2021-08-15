package CustomAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutplanner.AlterExerciseActivity;
import com.example.workoutplanner.Constants;
import com.example.workoutplanner.R;
import com.example.workoutplanner.RoutineExerciseEditDialog;
import com.example.workoutplanner.Utils;

import java.util.ArrayList;

import Entities.Exercise;
import Entities.RoutineExercise;

public class RoutineExerciseAdapter extends RecyclerView.Adapter<RoutineExerciseAdapter.ViewHolder> {

    Context context;
    Activity activity;
    ArrayList<RoutineExercise> routineExercises;
    FragmentManager fragmentManager;

    public RoutineExerciseAdapter(Activity activity, Context context, ArrayList<RoutineExercise> routineExercises, FragmentManager fragmentManager){
        this.activity = activity;
        this.context = context;
        this.routineExercises = routineExercises;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.routine_exercise_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RoutineExerciseAdapter.ViewHolder holder, int position) {
        RoutineExercise currentRoutineExercise = routineExercises.get(position);
        holder.tvSelectedExerciseName.setText(String.valueOf(currentRoutineExercise.getExercise().getName()));
        holder.tvRoutineExerciseReps.setText(String.valueOf((currentRoutineExercise.getReps() == 0) ? "-" :currentRoutineExercise.getReps()));
        holder.tvRoutineExerciseTimeOn.setText((currentRoutineExercise.getTimeOn() == 0) ? "-" : Utils.ConvertDecimalsToMinutes(currentRoutineExercise.getTimeOn()));
        holder.tvRoutineExerciseTimeOff.setText((currentRoutineExercise.getTimeOff() == 0) ? "-" : Utils.ConvertDecimalsToMinutes(currentRoutineExercise.getTimeOff()));
        holder.tvRoutineExerciseOrderNo.setText("#" + currentRoutineExercise.getOrderNumber());

        holder.routineExerciseListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoutineExerciseEditDialog editDialog = new RoutineExerciseEditDialog(currentRoutineExercise, position);
                editDialog.show(fragmentManager, "editDialog");
                Utils.ToastMessage(context, "You clicked on" + currentRoutineExercise.getExercise().getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return routineExercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSelectedExerciseName, tvRoutineExerciseReps, tvRoutineExerciseTimeOn, tvRoutineExerciseTimeOff, tvRoutineExerciseOrderNo;
        LinearLayout routineExerciseListLayout;
        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            tvSelectedExerciseName = itemView.findViewById(R.id.tvSelectedExerciseName);
            tvRoutineExerciseReps = itemView.findViewById(R.id.tvRoutineExerciseReps);
            tvRoutineExerciseTimeOn = itemView.findViewById(R.id.tvRoutineExerciseTimeOn);
            tvRoutineExerciseTimeOff = itemView.findViewById(R.id.tvRoutineExerciseTimeOff);
            tvRoutineExerciseOrderNo = itemView.findViewById(R.id.tvRoutineExerciseOrderNo);

            routineExerciseListLayout = itemView.findViewById(R.id.routineExerciseListLayout);
        }
    }
}
