package com.code.Entity;

import java.io.Serializable;

public class PaperInfo implements Serializable {

    private Integer id;
    private String taskname;
    private String stuname;
    private String state;
    private String tutorname;
    private Integer tutorid;
    private String type;
    private String crosstutor;
    private String ktgroup;
    private String lwgroup;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCrosstutor() {
        return crosstutor;
    }

    public void setCrosstutor(String crosstutor) {
        this.crosstutor = crosstutor;
    }

    public String getTutorname() {
        return tutorname;
    }

    public void setTutorname(String tutorname) {
        this.tutorname = tutorname;
    }


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTutorid() {
        return tutorid;
    }

    public void setTutorid(Integer tutorid) {
        this.tutorid = tutorid;
    }

    public String getKrgroup() {
        return ktgroup;
    }

    public void setKrgroup(String ktgroup) {
        this.ktgroup = ktgroup;
    }

    public String getLwgroup() {
        return lwgroup;
    }

    public void setLwgroup(String lwgroup) {
        this.lwgroup = lwgroup;
    }
}
