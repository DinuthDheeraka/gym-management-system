package view.tm;

public class MyOrderProductTM {
    private String itemCode;
    private String itemType;
    private String description;
    private double buyingPrice;
    private int qty;

    public MyOrderProductTM(String itemCode, String itemType, String description, double buyingPrice, int qty) {
        this.itemCode = itemCode;
        this.itemType = itemType;
        this.description = description;
        this.buyingPrice = buyingPrice;
        this.qty = qty;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
