package view.tm;

public class Member_Attendance_TM {
    private String memberId;
    private String name;
    private String address;
    private String email;
    private String teleNo;
    private int age;
    private String joinDate;
    private String scheduleId;
    private String trainerId;
    private double height;
    private double weight;

    Member_Attendance_TM(){}

    public Member_Attendance_TM(String memberId, String name, String address, String email, String teleNo, int age, String joinDate,
                                String scheduleId, String trainerId, double height, double weight) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.teleNo = teleNo;
        this.age = age;
        this.joinDate = joinDate;
        this.scheduleId = scheduleId;
        this.trainerId = trainerId;
        this.height = height;
        this.weight = weight;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeleNo() {
        return teleNo;
    }

    public void setTeleNo(String teleNo) {
        this.teleNo = teleNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
