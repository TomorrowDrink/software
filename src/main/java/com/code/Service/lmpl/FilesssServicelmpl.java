package com.code.Service.lmpl;

import com.code.Entity.Filesss;
import com.code.Mapper.FilesssMapper;
import com.code.Service.FilesssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FilesssServicelmpl implements FilesssService {

    @Autowired
    FilesssMapper filesssMapper;

    @Override
    public List<Filesss> findAll() {
        return filesssMapper.findAll();
    }

    @Override
    public Filesss findFileById(Integer id) {
        return filesssMapper.findById(id);
    }

    @Override
    public Filesss findFileByfilename(String filename) {
        return filesssMapper.findByFilename(filename);
    }

    @Override
    public Filesss findFileByastype(String astype) {
        return filesssMapper.findByAstype(astype);
    }

    @Override
    public Filesss findFileBystate(String state) {
        return filesssMapper.findByState(state);
    }

    @Override
    public Filesss findFileBytaskname(String taskname) {
        return filesssMapper.findByTaskname(taskname);
    }

    @Override
    public void insert(Filesss filesss) {
        filesssMapper.insert(filesss);
    }

    @Override
    public List<Filesss> findFileByidAndastype(Integer id, String astype) {
        return filesssMapper.findByIdAndAstype(id,astype);
    }
}
