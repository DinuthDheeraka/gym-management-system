package view.tm;

public class SupplimentTM {
    private String id;
    private String description;
    private String type;
    private double unitPrice;
    private int qty;

    public SupplimentTM(String id, String description, String type, double unitPrice, int qty) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public boolean equals(String id){
        return this.id.equals(id);
    }
}
