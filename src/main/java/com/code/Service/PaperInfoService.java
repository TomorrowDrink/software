package com.code.Service;

import com.code.Entity.PaperInfo;

import java.util.List;

public interface PaperInfoService {

    public List<PaperInfo> getAll();
    public List<PaperInfo> getAlllunwen();
    public List<PaperInfo> findPaperInfoById(String id);
    public List<PaperInfo> findPaperInfoByTaskname(String taskname);
    public List<PaperInfo> findPaperInfoByStuname(String stuname);
    public List<PaperInfo> findPaperInfoByTutorname(String tutorname);
    public List<PaperInfo> findPaperInfoByState(String state);
    public List<PaperInfo> findPaperInfoByTaskAndState(String taskname,String state);
    public List<PaperInfo> findByCrosstutor(String crosstutor);

    public List<PaperInfo> findPaperInfoByMaxId();
    public void addRecord(PaperInfo paperInfo);
    public void delRecord(String stuname);
    public void editRecord(String stuname,String newtutorname,String newstate);
}
