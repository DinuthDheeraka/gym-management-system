package view.tm;

public class EquipmentTM {
    private String id;
    private String type;
    private String description;
    private int qoh;

    public EquipmentTM(String id, String type, String description, int qoh) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.qoh = qoh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQoh() {
        return qoh;
    }

    public void setQoh(int qoh) {
        this.qoh = qoh;
    }

    public boolean equals(String id){
        return this.id.equals(id);
    }
}
