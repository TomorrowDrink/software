package com.code.Entity;

import java.io.Serializable;

public class PaperInfo implements Serializable {

    private String taskname;
    private String stuname;
    private String state;
    private String tutorname;

    public String getTutorname() {
        return tutorname;
    }

    public void setTutorname(String tutorname) {
        this.tutorname = tutorname;
    }

    private String id;

    public PaperInfo() { }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PaperInfo{" +
                "taskname='" + taskname + '\'' +
                ", stuname='" + stuname + '\'' +
                ", state='" + state + '\'' +
                ", tutorname='" + tutorname + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
