package com.code.Service;

import com.code.Entity.PaperInfo;

import java.util.List;

public interface PaperInfoService {

    public List<PaperInfo> getAll();
    public PaperInfo findPaperInfoById(String id);
    public PaperInfo findPaperInfoByTaskname(String taskname);
    public PaperInfo findPaperInfoByStuname(String stuname);
    public PaperInfo findPaperInfoByTutorname(String tutorname);
    public PaperInfo findPaperInfoByState(String state);

}
