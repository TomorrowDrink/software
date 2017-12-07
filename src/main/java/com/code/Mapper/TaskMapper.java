package com.code.Mapper;

import com.code.Entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface TaskMapper {
    List<Task> getAll();
    List<Task> findByTaskname(@Param("taskname") String taskname);
    List<Task> findByTaskrate(@Param("taskrate") String taskrate);
    List<Task> findByTasktype(@Param("tasktype") String tasktype);
    List<Task> findByTutorname(@Param("tutorname") String tutorname);
    List<Task> findByTaskid(@Param("taskid") int taskid);
    List<Task> findByTaskstate(@Param("taskstate") String taskstate);
//    List<Task> fingByTasknameAndstate(@Param("taskname")String taskname,@Param("taskstate")String taskstate);

    void insertTask(Task task);

    void updataTask(Task task);
//    void updataTask(@Param("taskname")Task taskname,@Param("newtaskname")Task newtaskname,
//                    @Param("taskrate")Task taskrate,@Param("newtaskrate")Task newtaskrate,
//                    @Param("tasktype")Task tasktype,@Param("newtasktype")Task newtasktype,
//                    @Param("tutorname")Task tutorname,@Param("newtutorname")Task newtutorname,
//                    @Param("taskdescrib")Task taskdescrib,@Param("newtaskdescrib")Task newtaskdescrib,
//                    @Param("taskstate")Task taskstate,@Param("newtaskrate")Task newtaskstate);



}