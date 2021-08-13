package Entities;

import java.io.Serializable;

public class RoutineExercise implements Serializable {
    private int id, idRoutine, idExercise, orderNumber, reps;
    private float timeOn, timeOff;
    private Exercise exercise;

    public RoutineExercise(){

    }

    public RoutineExercise(int id,
                           int idRoutine,
                           int idExercise,
                           int orderNumber,
                           int reps,
                           float timeOn,
                           float timeOff
    ) {
        this.id = id;
        this.idRoutine = idRoutine;
        this.idExercise = idExercise;
        this.orderNumber = orderNumber;
        this.reps = reps;
        this.timeOn = timeOn;
        this.timeOff = timeOff;
    }

    public RoutineExercise(int idRoutine,
                           int idExercise,
                           int orderNumber,
                           int reps,
                           float timeOn,
                           float timeOff
    ) {
        this.idRoutine = idRoutine;
        this.idExercise = idExercise;
        this.orderNumber = orderNumber;
        this.reps = reps;
        this.timeOn = timeOn;
        this.timeOff = timeOff;
    }

    public RoutineExercise(Exercise exercise) {
        this.idExercise = exercise.getId();
        this.exercise = exercise;
        this.orderNumber = 0;
        this.reps = 0;
        this.timeOn = 0;
        this.timeOff = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRoutine() {
        return idRoutine;
    }

    public void setIdRoutine(int idRoutine) {
        this.idRoutine = idRoutine;
    }

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public float getTimeOff() {
        return timeOff;
    }

    public void setTimeOff(float timeOff) {
        this.timeOff = timeOff;
    }

    public float getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(float timeOn) {
        this.timeOn = timeOn;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
