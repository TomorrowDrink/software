package com.code.Mapper;

import com.code.Entity.PaperInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperInfoMapper {

/*    @Select("select * from PaperInfo where taskname = #{taskname}")
     PaperInfo findPaperInfoByTaskname(@Param("taskname")String taskname);*/

    /*查询记录*/
    List<PaperInfo> getAll(/*@Param("page") *//*PageInfo page*/);

    List<PaperInfo> findByTaskname(@Param("taskname") String taskname);
    List<PaperInfo> findByTutorname(@Param("tutorname") String tutorname);
    List<PaperInfo> findByCrosstutor(@Param("crosstutor") String crosstutor);
    List<PaperInfo> findById(@Param("id") String id);
    List<PaperInfo> findByStuname(@Param("stuname") String stuname);
    List<PaperInfo> findByState(@Param("state") String state);
    List<PaperInfo> findByTaskAndState(@Param("taskname") String taskname,@Param("state") String state);
    List<PaperInfo> findByMaxId();
    List<PaperInfo> findByStunameAndType(@Param("stuname") String stuname,@Param("type") String type);
    List<PaperInfo> findByTasknameAndType(@Param("taskname") String taskname,@Param("type") String type);
    List<PaperInfo> findByTutoridStateType(@Param("tutorid") Integer tutorid,@Param("state") String state,@Param("type") String type);
    List<PaperInfo> findByTutoridAndType(@Param("tutorid") Integer tutorid,@Param("type") String type);
    List<PaperInfo> findByStateAndType(@Param("state") String state,@Param("type") String type);

    PaperInfo findScores(@Param("stuid") int stuid);
    PaperInfo findScoreById(@Param("id") int id);
    PaperInfo findCommentById(@Param("id") int id);

    /*新增记录*/
    void addRecord(PaperInfo paperInfo);
    /*删除记录*/
    void delRecord(@Param("stuname") String stuname);
    /*更新记录*/
    void editRecord(@Param("state") String state, @Param("tutorname") String tutorname,@Param("tutorid") Integer tutorid,@Param("type") String type, @Param("stuname") String stuname);
    void editCrosstutor(@Param("crosstutor") String crosstutor, @Param("id") int id);
    void editKtgroup(@Param("ktgroup") String ktgroup, @Param("id") int id);
    void editScoreAndComment(@Param("score") int score, @Param("comment") String comment,@Param("id") int id);

    List<PaperInfo> getAlllunwen();
    List<PaperInfo> getAllkt();
    List<PaperInfo> getAllwx();


}
