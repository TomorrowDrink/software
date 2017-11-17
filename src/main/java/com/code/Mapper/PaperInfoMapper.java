package com.code.Mapper;

import com.code.Entity.PaperInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperInfoMapper {

/*    @Select("select * from PaperInfo where taskname = #{taskname}")
     PaperInfo findPaperInfoByTaskname(@Param("taskname")String taskname);*/
    List<PaperInfo> getAll(/*@Param("page") *//*PageInfo page*/);

    PaperInfo findByTaskname(@Param("taskname") String taskname);
    PaperInfo findByTutorname(@Param("tutorname") String tutorname);
    PaperInfo findById(@Param("id") String id);
    PaperInfo findByStuname(@Param("stuname") String stuname);
    PaperInfo findByState(@Param("state") String state);


}
