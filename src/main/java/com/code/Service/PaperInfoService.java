package com.code.Service;

import com.code.Entity.PaperInfo;

import java.util.List;

public interface PaperInfoService {

    public List<PaperInfo> getAll();
    public List<PaperInfo> getAlllunwen();
    public List<PaperInfo> getAllkt();
    public List<PaperInfo> getAllwx();

    public PaperInfo findPaperInfoById(int id);
    public List<PaperInfo> findPaperInfoByTaskname(String taskname);
    public List<PaperInfo> findPaperInfoByStuname(String stuname);
    public List<PaperInfo> findPaperInfoByTutorname(String tutorname);
    public List<PaperInfo> findPaperInfoByState(String state);
    public List<PaperInfo> findPaperInfoByTaskAndState(String taskname,String state);
    public List<PaperInfo> findByCrosstutor(String crosstutor);
    public List<PaperInfo> findPaperInfoByStunameAndType(String stuname,String type);
    public List<PaperInfo> findPaperInfoByTasknameAndType(String taskname,String type);
    public List<PaperInfo> findPaperInfoByTutoridTypeState(int tutorid,String state,String type);
    public List<PaperInfo> findPaperInfoByStateAndType(String state,String type);
    public List<PaperInfo> findPaperInfoByTutoridAndType(int tutorid,String type);

    public List<PaperInfo> findPaperInfoByMaxId();
    public void addRecord(PaperInfo paperInfo);
    public void delRecord(String stuname);
    public void editRecord(String state,String tutorname,int tutorid,String type,String stuname);
    public void editCrosstutor(String crosstutor,int id);
    public void editKtgroup(String ktgroup,int id);
    public void editScoreAndComment(int score,String comment,int id);
    public void editState(int id, String state);


    public int findScores(int stuid);
    public PaperInfo findScoreById(int id);
    public PaperInfo findCommentById(int id);
}
