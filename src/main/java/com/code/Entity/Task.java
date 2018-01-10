package com.code.Entity;

import java.io.Serializable;

public class Task implements Serializable {
    private String taskname;
    private String tutorname;
    private String taskrate;
    private String taskdescrib;
    private int taskmaxchoose;
    private String tasktype;
    private int taskid;
    private String taskstate;
    private int tutorid;

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTutorname() {
        return tutorname;
    }

    public void setTutorname(String tutorname) {
        this.tutorname = tutorname;
    }

    public String getTaskrate() {
        return taskrate;
    }

    public void setTaskrate(String taskrate) {
        this.taskrate = taskrate;
    }

    public String getTaskdescrib() {
        return taskdescrib;
    }

    public void setTaskdescrib(String taskdescrib) {
        this.taskdescrib = taskdescrib;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    public int getTaskmaxchoose() {
        return taskmaxchoose;
    }
    public void setTaskmaxchoose(int taskmaxchoose) {
        this.taskmaxchoose = taskmaxchoose;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public int getTaskid() {
        return taskid;
    }
    public String getTaskstate() {
        return taskstate;
    }

    public void setTaskstate(String taskstate) {
        this.taskstate = taskstate;
    }

    public int getTutorid() {
        return tutorid;
    }

    public void setTutorid(int tutorid) {
        this.tutorid = tutorid;
    }

    public Task(){}

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskname + '\'' +
                ", tutorname='" + tutorname + '\'' +
                ", taskrate'" + taskrate+ '\'' +
                ", tasktype='" + tasktype + '\'' +
                ", taskid='" + taskid + '\'' +
                "taskmaxchoose='" + taskmaxchoose + '\'' +
                '}';
    }
}
