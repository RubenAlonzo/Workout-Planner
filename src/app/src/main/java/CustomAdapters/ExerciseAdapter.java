package CustomAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.workoutplanner.AlterExerciseActivity;
import com.example.workoutplanner.R;
import java.util.ArrayList;
import Entities.Exercise;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{

    Context context;
    Activity activity;
    ArrayList<Exercise> exercises;
    Animation animation;

    public ExerciseAdapter(Activity activity, Context context, ArrayList<Exercise> exercises){
        this.activity = activity;
        this.context = context;
        this.exercises = exercises;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exercise_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ExerciseAdapter.ViewHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        holder.tvExerciseName.setText( String.valueOf(currentExercise.getName()));
        holder.tvTargetMuscles.setText(String.valueOf(currentExercise.getTargetMuscles()));

        if(!currentExercise.getRefLink().isEmpty()){
            holder.imgLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(currentExercise.getRefLink()));
                    activity.startActivityForResult(intent, 1);
                }
            });
            holder.imgLink.setVisibility(View.VISIBLE);
            holder.imgLinkInactive.setVisibility(View.INVISIBLE);
        }
        else {
            holder.imgLink.setVisibility(View.INVISIBLE);
            holder.imgLinkInactive.setVisibility(View.VISIBLE);
        }

        holder.exerciseListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlterExerciseActivity.class);
                intent.putExtra("exercise", currentExercise);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTargetMuscles, tvExerciseName;
        ImageView imgLink, imgLinkInactive;
        LinearLayout exerciseListLayout;
        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            tvTargetMuscles = itemView.findViewById(R.id.tvTargetMuscles);
            tvExerciseName = itemView.findViewById(R.id.tvExerciseName);
            imgLink = itemView.findViewById(R.id.imgLink);
            imgLinkInactive = itemView.findViewById(R.id.imgLinkInactive);
            exerciseListLayout = itemView.findViewById(R.id.exerciseListLayout);

            animation = AnimationUtils.loadAnimation(context, R.anim.rows_up);
            exerciseListLayout.setAnimation(animation);
        }
    }
}
