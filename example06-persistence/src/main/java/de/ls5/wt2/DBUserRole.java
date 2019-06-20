package de.ls5.wt2;

import java.util.Date;
import javax.persistence.Entity;

@Entity
public class DBUserRole extends DBIdentified {

    private String rolename;
    private String email;

    public String getRolename() {
      return this.rolename;
    }
    public void setRolename(String rolename) {
      this.rolename = rolename;
    }

    public String getEmail() {
      return this.email;
    }
    public void setEmail(String email) {
      this.email = email;
    }
}

