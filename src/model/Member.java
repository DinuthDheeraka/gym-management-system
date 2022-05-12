package model;

public class Member {
    private String member_Id;
    private String name;
    private String address;
    private String email;
    private String tele;
    private String scheduleId;
    private int age;
    private double weight;
    private double height;
    private String joinDate;

    public Member(){}

    public Member(String member_Id, String name, String address, String email, String tele,
                  String scheduleId, int age, double weight, double height, String joinDate) {
        this.member_Id = member_Id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.tele = tele;
        this.scheduleId = scheduleId;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.joinDate = joinDate;
    }

    public String getMember_Id() {
        return member_Id;
    }

    public void setMember_Id(String member_Id) {
        this.member_Id = member_Id;
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

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }
}
