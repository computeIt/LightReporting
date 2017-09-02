package entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Андрей on 07.08.2017.
 */
@Entity
public class User {             //superclass for superAdmin and companyOwner
    @Id
    private String email;       //uniqID
    private String password;    //hash
    private Role role;
    private String name;
    private String phone;

    public User() {
    }

    public User(String email, String password, Role role, String name, String phone) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
