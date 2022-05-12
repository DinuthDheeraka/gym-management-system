package view.tm;

public class MemberPayementTM {
    private String payementId;
    private String settleDate;
    private String settleTime;
    private String month;
    private double amount;
    private String memberId;

    public MemberPayementTM(String payementId, String settleDate, String settleTime, String month, double amount, String memberId) {
        this.payementId = payementId;
        this.settleDate = settleDate;
        this.settleTime = settleTime;
        this.month = month;
        this.amount = amount;
        this.memberId = memberId;
    }

    public String getPayementId() {
        return payementId;
    }

    public void setPayementId(String payementId) {
        this.payementId = payementId;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
