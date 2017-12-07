package com.code.Service;

import com.code.Entity.Task;

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
}
