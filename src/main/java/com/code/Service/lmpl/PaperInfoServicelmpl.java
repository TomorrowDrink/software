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
    public PaperInfo findPaperInfoById(String id) {
        return paperInfoMapper.findById(id);
    }

    @Override
    public PaperInfo findPaperInfoByTaskname(String taskname) {
        return paperInfoMapper.findByTaskname(taskname);
    }

    @Override
    public PaperInfo findPaperInfoByStuname(String stuname) {
        return paperInfoMapper.findByStuname(stuname);
    }

    @Override
    public PaperInfo findPaperInfoByTutorname(String tutorname) { return paperInfoMapper.findByTutorname(tutorname); }

    @Override
    public PaperInfo findPaperInfoByState(String state) {
        return paperInfoMapper.findByState(state);
    }
}
