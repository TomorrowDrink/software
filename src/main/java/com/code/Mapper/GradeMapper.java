package com.code.Mapper;

import com.code.Entity.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GradeMapper {

    /*查询记录*/
    List<Grade> getAll();
    List<Grade> findByTname(@Param("tname") String tname);
    List<Grade> findGradesByTutorid(@Param("tutorid") int tutorid);


    void editTscore(@Param("tscore") int tscore, @Param("sno") int sno);
    void editIsgreat();



}
