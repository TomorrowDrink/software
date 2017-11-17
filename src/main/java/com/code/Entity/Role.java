package com.code.Entity;

/**
 * Created by alison on 17-11-4.
 */
public class Role {
    private Integer rid;
    private String rolename;

    public Integer getId() {
        return rid;
    }

    public void setId(Integer id) {
        this.rid = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
