package view.tm;

public class MemberFitnessReportTM {
    private String reportId;
    private String memberId;
    private String date;
    private double weight;
    private double height;

    public MemberFitnessReportTM(String reportId, String memberId, String date, double weight, double height) {
        this.reportId = reportId;
        this.memberId = memberId;
        this.date = date;
        this.weight = weight;
        this.height = height;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
