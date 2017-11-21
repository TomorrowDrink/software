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

    List<PaperInfo> findByTaskname(@Param("taskname") String taskname);
    List<PaperInfo> findByTutorname(@Param("tutorname") String tutorname);
    List<PaperInfo> findById(@Param("id") String id);
    List<PaperInfo> findByStuname(@Param("stuname") String stuname);
    List<PaperInfo> findByState(@Param("state") String state);
    List<PaperInfo> findByTaskAndState(@Param("taskname") String taskname,@Param("state") String state);


}
