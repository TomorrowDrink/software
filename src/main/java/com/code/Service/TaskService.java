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
    public void chooseTask(int stu_id, String stu_name,int task_id,String task_name);
//    public List<Task_s> findTaskidBysid(int stu_id);
//    public int findTaskidBysid(int stu_id);
    public List<Task_s> findbystuid(int stu_id);

}
