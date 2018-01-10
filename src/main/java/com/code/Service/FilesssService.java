package com.code.Service;

import com.code.Entity.Filesss;

import java.util.List;

public interface FilesssService {

    public List<Filesss> findAll();
    public Filesss findFileById(Integer id);
    public List<Filesss> findFileByidAndastype(Integer id,String astype);
    public Filesss findFileByfilename(String filename);
    public Filesss findFileByastype(String astype);
    public Filesss findFileBystate(String state);
    public Filesss findFileBytaskname(String taskname);
    public void insert(Filesss filesss);
}
