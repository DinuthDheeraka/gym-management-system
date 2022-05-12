package view.tm;

public class ExerciseTM {
    private String exerciseId;
    private String description;
    private String note;

    ExerciseTM(){}

    public ExerciseTM(String exerciseId, String description, String note) {
        this.exerciseId = exerciseId;
        this.description = description;
        this.note = note;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean equals(String id){
        return this.exerciseId.equals(id);
    }
}
