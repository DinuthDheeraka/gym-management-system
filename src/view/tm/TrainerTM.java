package view.tm;

public class TrainerTM {
    private String trainerNic;
    private String trainerName;
    private String trainerAddress;
    private String trainerTele;
    private String trainerEmail;
    private int age;

    TrainerTM(){}

    public TrainerTM(String trainerNic, String trainerName,
                     String trainerAddress, String trainerTele, String trainerEmail, int age) {
        setTrainerNic(trainerNic);
        setTrainerName(trainerName);
        setTrainerAddress(trainerAddress);
        setTrainerTele(trainerTele);
        setTrainerEmail(trainerEmail);
        this.setAge(age);
    }

    public String getTrainerNic() {
        return trainerNic;
    }

    public void setTrainerNic(String trainerNic) {
        this.trainerNic = trainerNic;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerAddress() {
        return trainerAddress;
    }

    public void setTrainerAddress(String trainerAddress) {
        this.trainerAddress = trainerAddress;
    }

    public String getTrainerTele() {
        return trainerTele;
    }

    public void setTrainerTele(String trainerTele) {
        this.trainerTele = trainerTele;
    }

    public String getTrainerEmail() {
        return trainerEmail;
    }

    public void setTrainerEmail(String trainerEmail) {
        this.trainerEmail = trainerEmail;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
