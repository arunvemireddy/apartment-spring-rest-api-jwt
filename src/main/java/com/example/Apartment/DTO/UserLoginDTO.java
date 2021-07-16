package com.example.Apartment.DTO;

import java.util.Set;

/**
 * @author arun vemireddy
 */
public class UserLoginDTO {

    private String eusername;
    private String ename;
    private Set<String> roles;

    public String getEusername() {
        return eusername;
    }

    public void setEusername(String eusername) {
        this.eusername = eusername;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
