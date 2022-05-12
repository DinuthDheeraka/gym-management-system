package view.tm;

public class MemberPayementStatusTM {
    private String memberId;
    private String membername;
    private String email;
    private String address;
    private String tele;
    private String joinDate;

    public MemberPayementStatusTM(String memberId, String membername, String email, String address, String tele, String joinDate) {
        this.memberId = memberId;
        this.membername = membername;
        this.email = email;
        this.address = address;
        this.tele = tele;
        this.joinDate = joinDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }
}
