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
    public void editTscore(int tscore, int sno) {
        gradeMapper.editTscore(tscore,sno);
    }

    @Override
    public void editIsgreat() {
        gradeMapper.editIsgreat();
    }
}
