package com.code.Service.lmpl;

import com.code.Entity.PaperInfo;
import com.code.Entity.Task;
import com.code.Entity.User;
import com.code.Mapper.TaskMapper;
import com.code.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

@Service
public class TaskServicelmpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;

    @Override
    public List<Task> getAll() {
        System.out.println(taskMapper.getAll());
        return taskMapper.getAll();
    }
    @Override
    public List<Task> findTaskByTaskid(int taskid){return taskMapper.findByTaskid(taskid);}
    @Override
    public List<Task> findTaskByTaskname(String taskname){
        return taskMapper.findByTaskname(taskname);
    }
    @Override
    public  List<Task> findTaskByTaskrate(String taskrate){
        return taskMapper.findByTaskrate(taskrate);
    }
    @Override
    public  List<Task> findTaskByTasktype(String tasktype){
        return taskMapper.findByTasktype(tasktype);
    }
    @Override
    public List<Task> findTaskByTutorname(String tutorname){
        return taskMapper.findByTutorname(tutorname);
    }
    @Override
    public List<Task>findTaskByTaskstate(String taskstae){return taskMapper.findByTaskstate(taskstae);}
    @Override
//    public List<Task> findTaskByTasknameAndstate(String taskname, String taskstate) {
//        return taskMapper.fingByTasknameAndstate(taskname,taskstate);
//    }


    public void  insertTask(Task task){
        taskMapper.insertTask(task);
    }
    public void  updataTask(Task task){ taskMapper.updataTask(task); }


}

