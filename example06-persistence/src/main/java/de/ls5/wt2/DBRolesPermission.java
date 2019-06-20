package de.ls5.wt2;

import java.util.Date;
import javax.persistence.Entity;

@Entity
public class DBRolesPermission extends DBIdentified {

    private String permission;
    private String rolename;

    public String getPermission() {
      return this.permission;
    }
    public void setPermission(String permission) {
      this.permission = permission;
    }

    public String getRolename() {
      return this.rolename;
    }
    public void setRolename(String rolename) {
      this.rolename = rolename;
    }

}

