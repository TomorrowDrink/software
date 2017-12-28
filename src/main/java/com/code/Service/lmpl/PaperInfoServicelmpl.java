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
    public List<PaperInfo> findByCrosstutor(String crosstutor) {
        return paperInfoMapper.findByCrosstutor(crosstutor);
    }

    @Override
    public List<PaperInfo> getAlllunwen() {
        return paperInfoMapper.getAlllunwen();
    }

    @Override
    public List<PaperInfo> getAllkt() {
        return paperInfoMapper.getAllkt();
    }

    @Override
    public List<PaperInfo> getAllwx() {
        return paperInfoMapper.getAllwx();
    }

    @Override
    public List<PaperInfo> findPaperInfoByStunameAndType(String stuname, String type) {
        return paperInfoMapper.findByStunameAndType(stuname,type);
    }

    @Override
    public List<PaperInfo> findPaperInfoByTasknameAndType(String taskname, String type) {
        return paperInfoMapper.findByTasknameAndType(taskname,type);
    }

    @Override
    public List<PaperInfo> findPaperInfoByStateAndType(String state, String type) {
        return paperInfoMapper.findByStateAndType(state,type);
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

    @Override
    public List<PaperInfo> findPaperInfoByTutoridTypeState(int tutorid, String state,String type) {
        return paperInfoMapper.findByTutoridStateType(tutorid,state,type);
    }

    @Override
    public List<PaperInfo> findPaperInfoByMaxId(){
        return paperInfoMapper.findByMaxId();
    }

    @Override
    public void addRecord(PaperInfo paperInfo) {
        paperInfoMapper.addRecord(paperInfo);
    }

    @Override
    public void delRecord(String stuname) {
        paperInfoMapper.delRecord(stuname);
    }

    @Override
    public void editRecord(String state, String tutorname, int tutorid, String type, String stuname) {
        paperInfoMapper.editRecord(state,tutorname,tutorid,type,stuname);
    }

    @Override
    public void editCrosstutor(String crosstutor, int id) {
        paperInfoMapper.editCrosstutor(crosstutor,id);
    }


}
