package data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Андрей on 12.08.2017.
 */

public class CompanyData {
    @Id
    private String idInMongo;
    private String companyName;
    private String address;
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;

    public CompanyData() {
    }

    public CompanyData(String idInMongo, String companyName, String address, String ownerName, String ownerPhone, String ownerEmail) {
        this.idInMongo = idInMongo;
        this.companyName = companyName;
        this.address = address;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.ownerEmail = ownerEmail;
    }

    public String getIdInMongo() {
        return idInMongo;
    }

    public void setIdInMongo(String idInMongo) {
        this.idInMongo = idInMongo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }


}

