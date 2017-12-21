package com.code.Mapper;

import com.code.Entity.Filesss;
import org.apache.ibatis.annotations.*;


import java.util.List;


public interface FilesssMapper {

    List<Filesss> findAll();

    Filesss findById(@Param("id")Integer id);
    Filesss findByFilename(@Param("filename")String filename);
    List<Filesss> findByIdAndAstype(@Param("id")Integer id,@Param("astype")String astype);
    Filesss findByAstype(@Param("astype")String astype);
    Filesss findByState(@Param("state")String state);
    Filesss findByTaskname(@Param("taskname")String taskname);

//    void insert(@Param("filename")String filename);

    void insert(Filesss filesss);

}
