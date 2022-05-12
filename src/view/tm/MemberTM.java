package view.tm;

public class MemberTM {
    private String memberId;
    private String memberName;
    private String memberAddress;
    private String memberEmail;
    private String MemberTele;
    private int memberAge;
    private String memberJoinedDate;
    private String memberScheduleId;
    private String memberTrainerId;
    private double memberHeight;
    private double memberWeight;

    MemberTM(){
    }

    public MemberTM(String memberId, String memberName, String memberAddress, String memberEmail, String memberTele, int memberAge, String memberJoinedDate,
                    String memberScheduleId, String memberTrainerId, double memberHeight, double memberWeight) {
        this.setMemberId(memberId);
        this.setMemberName(memberName);
        this.setMemberAddress(memberAddress);
        this.setMemberEmail(memberEmail);
        setMemberTele(memberTele);
        this.setMemberAge(memberAge);
        this.setMemberJoinedDate(memberJoinedDate);
        this.setMemberScheduleId(memberScheduleId);
        this.setMemberTrainerId(memberTrainerId);
        this.setMemberHeight(memberHeight);
        this.setMemberWeight(memberWeight);
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberTele() {
        return MemberTele;
    }

    public void setMemberTele(String memberTele) {
        MemberTele = memberTele;
    }

    public int getMemberAge() {
        return memberAge;
    }

    public void setMemberAge(int memberAge) {
        this.memberAge = memberAge;
    }

    public String getMemberJoinedDate() {
        return memberJoinedDate;
    }

    public void setMemberJoinedDate(String memberJoinedDate) {
        this.memberJoinedDate = memberJoinedDate;
    }

    public String getMemberScheduleId() {
        return memberScheduleId;
    }

    public void setMemberScheduleId(String memberScheduleId) {
        this.memberScheduleId = memberScheduleId;
    }

    public String getMemberTrainerId() {
        return memberTrainerId;
    }

    public void setMemberTrainerId(String memberTrainerId) {
        this.memberTrainerId = memberTrainerId;
    }

    public double getMemberHeight() {
        return memberHeight;
    }

    public void setMemberHeight(double memberHeight) {
        this.memberHeight = memberHeight;
    }

    public double getMemberWeight() {
        return memberWeight;
    }

    public void setMemberWeight(double memberWeight) {
        this.memberWeight = memberWeight;
    }
}
