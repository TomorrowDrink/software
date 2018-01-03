package com.code.Service;

import com.code.Entity.Task;
import com.code.Entity.Task_s;

import java.security.PublicKey;
import java.util.List;

public interface TaskService {
    public List<Task> getAll();
    public List<Task> findTaskByTaskname(String taskname);
    public List<Task> findTaskByTaskrate(String taskrate);
    public List<Task> findTaskByTasktype(String tasktype);
    public List<Task> findTaskByTutorname(String tutorname);
    public List<Task> findTaskByTaskid(int taskid);
    public List<Task> findTaskByTaskstate(String taskstate);
//    public List<Task> findTaskByTasknameAndstate(String taskname,String taskstate);

    public void  insertTask(Task task);
    public void updataTask(Task task);
    public void delTask(String taskid);
    public void s_delMyTask(String task_id);
    public void chooseTask(int stu_id, String stu_name,int task_id,String task_name);
//    public List<Task_s> findTaskidBysid(int stu_id);
//    public int findTaskidBysid(int stu_id);
    public List<Task_s> findbystuid(int stu_id);
    public void updataTaskState(String passtaskid );

    public  List<Task> findTaskBytutorid(int tutor_id);
//    教师课题查询
    public List<Task> findTaskFor(int tutor_id,String task_type,String task_state);
    public List<Task> findtypeTaskFor(int tutor_id,String task_typ );
    public List<Task> findstateTaskFor(int tutor_id,String task_state);
    public List<Task> findmyallTaskFor(int tutor_id);
//    学生课题查询
    public List<Task> s_findTask(String task_type);

    public List<Task> a_findTaskByTypeState(String task_type,String task_state);
    public List<Task> a_findTaskByType(String task_type);
    public List<Task> a_findTaskByState(String task_state);
}
