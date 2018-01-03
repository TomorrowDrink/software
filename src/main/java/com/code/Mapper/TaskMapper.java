package com.code.Mapper;

import com.code.Entity.Task;
import com.code.Entity.Task_s;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
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
    List<Task_s> findbystuid(@Param("stu_id") int stu_id);




    List<Task> findTaskBytutorid(@Param("tutor_id") int tutor_id);


    /**
     *教师课题查询
     */
    List<Task> findTaskFor(@Param("tutor_id") int tutor_id,
                           @Param("task_type")String task_type,
                           @Param("task_state")String task_state);
    List<Task> findtypeTaskFor(@Param("tutor_id") int tutor_id,
                               @Param("task_type") String task_type);
    List<Task> findstateTaskFor(@Param("tutor_id")int tutor_id,
                                @Param("task_state")String task_state);
    List<Task> findmyallTaskFor(@Param("tutor_id") int tutor_id);

    /**
     *学生课题查询
     */
    List<Task> s_findTask(@Param("task_type") String task_type);

    /**
     *管理员课题查询
     */
    List<Task> a_findTaskByTypeState(@Param("task_type") String task_type,
                                   @Param("task_state") String task_state);
    List<Task> a_findTaskByType(@Param("task_type") String task_type);
    List<Task> a_findTaskByState(@Param("task_state") String task_state);



    void insertTask(Task task);
    void delTask(@Param("taskid") String taskid);
    void s_delMyTask(@Param("task_id")String task_id);
    void chooseTask(@Param("stu_id") int stuid,
                    @Param("stu_name") String stuname,
                    @Param("task_id") int taskid,
                    @Param("task_name")String taskname);
    void updataTask(Task task);
    void updataTaskState(@Param("passtaskid")String passtaskid);




}