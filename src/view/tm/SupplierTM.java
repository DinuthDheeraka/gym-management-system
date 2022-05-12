package view.tm;

public class SupplierTM {
    private String id;
    private String companyName;
    private String email;
    private String tele;
    private String address;

    public SupplierTM(String id, String companyName, String email, String tele, String address) {
        this.id = id;
        this.companyName = companyName;
        this.email = email;
        this.tele = tele;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
