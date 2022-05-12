package view.tm;

public class Schedule_Exercise_TM {
    private String exerciseId;
    private String name;
    private String affectedArea;
    private String instructions;

    public Schedule_Exercise_TM(String exerciseId, String name, String affectedArea, String instructions) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.affectedArea = affectedArea;
        this.instructions = instructions;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAffectedArea() {
        return affectedArea;
    }

    public void setAffectedArea(String affectedArea) {
        this.affectedArea = affectedArea;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
