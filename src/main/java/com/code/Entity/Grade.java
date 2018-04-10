package com.code.Entity;

import java.io.Serializable;

public class Grade implements Serializable{
    private int gid;
    private int sno;
    private String sname;
    private int tscore;
    private int isgreat;
    private String tname;
    private String tutorname;
    private int tutorid;

    public Grade(int gid, int sno, String sname, int tscore, int isgreat, String tname, String tutorname, int tutorid) {
        this.gid = gid;
        this.sno = sno;
        this.sname = sname;
        this.tscore = tscore;
        this.isgreat = isgreat;
        this.tname = tname;
        this.tutorname = tutorname;
        this.tutorid = tutorid;
    }

    public Grade() {
    }

    public int getSno() {
    return sno;
  }

    public void setSno(int sno) {
    this.sno = sno;
  }

    public String getSname() {
    return sname;
  }

    public void setSname(String sname) {
    this.sname = sname;
  }

    public int getTscore() {
    return tscore;
  }

    public void setTscore(int tscore) {
    this.tscore = tscore;
  }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTutorname() {
        return tutorname;
    }

    public void setTutorname(String tutorname) {
        this.tutorname = tutorname;
    }

    public int getTutorid() {
        return tutorid;
    }

    public void setTutorid(int tutorid) {
        this.tutorid = tutorid;
    }

    public int getIsgreat() {
    return isgreat;
  }

    public void setIsgreat(int isgreat) {
    this.isgreat = isgreat;
  }
}
