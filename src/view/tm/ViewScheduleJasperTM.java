package view.tm;

public class ViewScheduleJasperTM {
    private String name;
    private String affect;
    private String instructions;

    public ViewScheduleJasperTM(String name, String affect, String instructions) {
        this.name = name;
        this.affect = affect;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAffect() {
        return affect;
    }

    public void setAffect(String affect) {
        this.affect = affect;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
