package com.code.Mapper;

import com.code.Entity.Task;
import com.code.Entity.Task_s;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface TaskMapper {
    List<Task> getAll();
    List<Task> findByTaskname(@Param("taskname") String taskname);
    List<Task> findByTaskrate(@Param("taskrate")String taskrate);
    List<Task> findByTasktype(@Param("tasktype")String tasktype);
    List<Task> findByTutorname(@Param("tutorname")String tutorname);
    List<Task> findByTaskid(@Param("taskid") int taskid);
    List<Task> findByTaskstate(@Param("taskstate")String taskstate);
//    List<Task> fingByTasknameAndstate(@Param("taskname")String taskname,@Param("taskstate")String taskstate);

    void insertTask(Task task);
    void delTask(@Param("taskid") String taskid);

//    List<Task_s> findTaskidBysid(@Param("stu_id") int stu_id);
//    int findTaskidBysid(@Param("stu_id") int stu_id);

    List<Task_s> findbystuid(@Param("stu_id") int stu_id);


    void chooseTask(@Param("stu_id") int stuid,
                    @Param("stu_name") String stuname,
                    @Param("task_id") int taskid,
                    @Param("task_name")String taskname);


    void updataTask(Task task);


}