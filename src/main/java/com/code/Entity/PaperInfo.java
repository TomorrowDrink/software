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
    private Integer score;
    private String comment;
    private Integer crossscore;
    private  String crosscomment;
    private Integer taskid;
    private Integer stuid;

    public PaperInfo(Integer id, String taskname, String stuname, String state, String tutorname, Integer tutorid, String type, String crosstutor, String ktgroup, String lwgroup, Integer score, String comment, Integer crossscore, String crosscomment, Integer taskid, Integer stuid) {
        this.id = id;
        this.taskname = taskname;
        this.stuname = stuname;
        this.state = state;
        this.tutorname = tutorname;
        this.tutorid = tutorid;
        this.type = type;
        this.crosstutor = crosstutor;
        this.ktgroup = ktgroup;
        this.lwgroup = lwgroup;
        this.score = score;
        this.comment = comment;
        this.crossscore = crossscore;
        this.crosscomment = crosscomment;
        this.taskid = taskid;
        this.stuid = stuid;
    }

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

    public String getKtgroup() {
        return ktgroup;
    }

    public void setKtgroup(String ktgroup) {
        this.ktgroup = ktgroup;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Integer getCrossscore() {
        return crossscore;
    }

    public void setCrossscore(Integer crossscore) {
        this.crossscore = crossscore;
    }

    public String getCrosscomment() {
        return crosscomment;
    }

    public void setCrosscomment(String crosscomment) {
        this.crosscomment = crosscomment;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public Integer getStuid() {
        return stuid;
    }

    public void setStuid(Integer stuid) {
        this.stuid = stuid;
    }
}
