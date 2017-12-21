package com.code.Entity;

import java.io.Serializable;

public class Filesss implements Serializable {

    private Integer id;
    private String filename;
    private String astype;
    private String state;
    private String taskname;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getAstype() {
        return astype;
    }
    public void setAstype(String astype) {
        this.astype = astype;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getTaskname() {
        return taskname;
    }
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

}
