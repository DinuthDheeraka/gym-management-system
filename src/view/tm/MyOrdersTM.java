package view.tm;

public class MyOrdersTM {
    private String orderId;
    private String date;
    private String time;
    private double totalCost;
    private String supplierId;

    public MyOrdersTM(String orderId, String date, String time, double totalCost, String supplierId) {
        this.orderId = orderId;
        this.date = date;
        this.time = time;
        this.totalCost = totalCost;
        this.supplierId = supplierId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
