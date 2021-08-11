package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Routine implements Serializable {
    private int id, sets;
    private float setRest, estimatedDuration;
    private String title, targetMuscles, day;
    private ArrayList<RoutineExercise> routineExercises;

    public Routine(int id,
                   String title,
                   String day,
                   String targetMuscles,
                   int sets,
                   float setRest,
                   float estimatedDuration
    ) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.sets = sets;
        this.targetMuscles = targetMuscles;
        this.setRest = setRest;
        this.estimatedDuration = estimatedDuration;
    }

    public Routine(String title,
                   String day,
                   String targetMuscles,
                   int sets,
                   float setRest,
                   float estimatedDuration
    ) {
        this.title = title;
        this.day = day;
        this.sets = sets;
        this.targetMuscles = targetMuscles;
        this.setRest = setRest;
        this.estimatedDuration = estimatedDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public float getSetRest() {
        return setRest;
    }

    public void setSetRest(float setRest) {
        this.setRest = setRest;
    }

    public float getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(float estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTargetMuscles() {
        return targetMuscles;
    }

    public void setTargetMuscles(String targetMuscles) {
        this.targetMuscles = targetMuscles;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<RoutineExercise> getRoutineExercises() {
        return routineExercises;
    }

    public void setRoutineExercises(ArrayList<RoutineExercise> routineExercises) {
        this.routineExercises = routineExercises;
    }
}
