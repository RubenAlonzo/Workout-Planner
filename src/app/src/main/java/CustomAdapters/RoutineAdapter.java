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
import androidx.recyclerview.widget.RecyclerView;
import com.example.workoutplanner.Constants;
import com.example.workoutplanner.R;
import com.example.workoutplanner.ViewRoutineActivity;
import java.util.ArrayList;
import Entities.Routine;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {

    Context context;
    Activity activity;
    ArrayList<Routine> routines;
    Animation animation;

    public RoutineAdapter(Activity activity, Context context, ArrayList<Routine> routines){
        this.activity = activity;
        this.context = context;
        this.routines = routines;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public RoutineAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.routine_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RoutineAdapter.ViewHolder holder, int position) {
        Routine currentExercise = routines.get(position);
        holder.tvRoutineTitle.setText(String.valueOf(currentExercise.getTitle()));
        holder.tvDay.setText(String.valueOf(currentExercise.getDay()));
        holder.tvEstimatedTime.setText("Estimated duration: " + currentExercise.getEstimatedDuration());
        holder.routineListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewRoutineActivity.class);
                intent.putExtra(Constants.ROUTINE_EXTRA, currentExercise);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEstimatedTime, tvRoutineTitle, tvDay;
        LinearLayout routineListLayout;
        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            tvEstimatedTime = itemView.findViewById(R.id.tvEstimatedTime);
            tvRoutineTitle = itemView.findViewById(R.id.tvRoutineTitle);
            tvDay = itemView.findViewById(R.id.tvDay);
            routineListLayout = itemView.findViewById(R.id.routineListLayout);
            animation = AnimationUtils.loadAnimation(context, R.anim.rows_up);
            routineListLayout.setAnimation(animation);
        }
    }
}
