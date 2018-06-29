package com.code.Service.lmpl;


import com.code.Entity.Grade;
import com.code.Mapper.GradeMapper;
import com.code.Service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServicelmpl implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;
    
    @Override
    public List<Grade> getAll() {
        System.out.println(gradeMapper.getAll());
        return gradeMapper.getAll();
    }

    @Override
    public List<Grade> findGradeByTname(String tname) {
        return gradeMapper.findByTname(tname);
    }

    @Override
    public List<Grade> findGradesByTutorid(int tutorid) {
        return gradeMapper.findGradesByTutorid(tutorid);
    }

    @Override
    public int findGradesBySno(int sno) {
        return gradeMapper.findGradesBySno(sno);
    }

    @Override
    public List<Grade> findGradesByTutoridIsgreat(int tutorid,int isgreat) {
        return gradeMapper.findGradesByTutoridIsgreat(tutorid,isgreat);
    }

    @Override
    public List<Grade> findGradesByIsgreat(int isgreat) {
        return gradeMapper.findGradesByIsgreat(isgreat);
    }

    @Override
    public void editTscore(int tscore, int sno) {
        gradeMapper.editTscore(tscore,sno);
    }

    @Override
    public void editIsgreat(int sno,int isgreat,String advice) {
        gradeMapper.editIsgreat(sno,isgreat,advice);
    }
}
