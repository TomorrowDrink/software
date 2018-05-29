package com.code.Service;

import com.code.Entity.Grade;
import com.code.Entity.PaperInfo;

import java.util.List;

public interface GradeService {

    /*查询记录*/
    public List<Grade> getAll();
    public List<Grade> findGradeByTname(String tname);
    public List<Grade> findGradesByTutorid(int tutorid);

    public void editTscore(int tscore,int sno);
    public void editIsgreat();


}
