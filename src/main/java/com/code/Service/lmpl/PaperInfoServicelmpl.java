package com.code.Service.lmpl;


import com.code.Entity.PaperInfo;
import com.code.Mapper.PaperInfoMapper;
import com.code.Service.PaperInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperInfoServicelmpl implements PaperInfoService{

    @Autowired
    private PaperInfoMapper paperInfoMapper;
    
    @Override
    public List<PaperInfo> getAll() {
        System.out.println(paperInfoMapper.getAll());
        return paperInfoMapper.getAll();
    }


    @Override
    public List<PaperInfo> findPaperInfoById(String id) {
        return paperInfoMapper.findById(id);
    }

    @Override
    public List<PaperInfo> findPaperInfoByTaskname(String taskname) {
        return paperInfoMapper.findByTaskname(taskname);
    }

    @Override
    public List<PaperInfo> findPaperInfoByStuname(String stuname) {
        return paperInfoMapper.findByStuname(stuname);
    }

    @Override
    public List<PaperInfo> findPaperInfoByTutorname(String tutorname) { return paperInfoMapper.findByTutorname(tutorname); }

    @Override
    public List<PaperInfo> findPaperInfoByState(String state) {
        return paperInfoMapper.findByState(state);
    }

    @Override
    public List<PaperInfo> findPaperInfoByTaskAndState(String taskname, String state) {
        return paperInfoMapper.findByTaskAndState(taskname,state);
    }
}
