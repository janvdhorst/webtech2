package de.ls5.wt2;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table
public class DBUser extends DBIdentified {

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;
    private String firstname;
    private String lastname;
    private String salt;

    public String getLastname() {
      return this.lastname;
    }
    public void setLastname(String lastname) {
      this.lastname = lastname;
    }
    public String getFirstname() {
      return this.firstname;
    }
    public void setFirstname(String firstname) {
      this.firstname = firstname;
    }
    public String getUsername() {
      return username;
    }
    public void setUsername(String username) {
      this.username = username;
    }

    public String getEmail() {
      return this.email;
    }
    public void setEmail(String email) {
      this.email = email;
    }

    public String getPassword() {
      return this.password;
    }
    public void setPassword(String password) {
      this.password = password;
    }

    public String getSalt() {
      return this.salt;
    }
    public void setSalt(String salt) {
      this.salt = salt;
    }
}

