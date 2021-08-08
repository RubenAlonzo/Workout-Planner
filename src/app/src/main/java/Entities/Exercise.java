package Entities;

import java.io.Serializable;

public class Exercise implements Serializable {
    private int id;
    private String name, targetMuscles, refLink;

    public Exercise(int id, String name, String targetMuscles, String refLink){
        this.id = id;
        this.name = name;
        this.targetMuscles = targetMuscles;
        this.refLink = refLink;
    }

    public Exercise(String name, String targetMuscles, String refLink){
        this.name = name;
        this.targetMuscles = targetMuscles;
        this.refLink = refLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetMuscles() {
        return targetMuscles;
    }

    public void setTargetMuscles(String targetMuscles) {
        this.targetMuscles = targetMuscles;
    }

    public String getRefLink() {
        return refLink;
    }

    public void setRefLink(String refLink) {
        this.refLink = refLink;
    }
}
