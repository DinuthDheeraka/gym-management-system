package controller;

public class JasperRevenue {
    private String month;
    private String totalIncome;
    private String totalExpenses;
    private String totalReve;

    public JasperRevenue(String month,String totalIncome,String totalExpenses,String totalReve) {
        this.setMonth(month);
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.totalReve = totalReve;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(String totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getTotalReve() {
        return totalReve;
    }

    public void setTotalReve(String totalReve) {
        this.totalReve = totalReve;
    }
}
