package com.code.Service.lmpl;

import com.code.Entity.PaperInfo;
import com.code.Entity.Task;
import com.code.Entity.Task_s;
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
    public List<Task> findTaskBytutorid(int tutorid){return taskMapper.findTaskBytutorid(tutorid);}


    public void  insertTask(Task task){
        taskMapper.insertTask(task);
    }
    public void  updataTask(Task task){ taskMapper.updataTask(task); }
    @Override
    public void delTask(String taskid){
        taskMapper.delTask(taskid);
    }
    @Override
    public void s_delMyTask(String task_id ,int stu_id){taskMapper.s_delMyTask(task_id,stu_id);}
    public void chooseTask(int stu_id,String stu_name,int task_id,String task_name  ){taskMapper.chooseTask(stu_id,stu_name,task_id,task_name);}

//    @Override
//    public List<Task_s> findTaskidBysid(int stu_id){return taskMapper.findTaskidBysid(stu_id);}

//    public int findTaskidBysid(int stu_id){return taskMapper.findTaskidBysid(stu_id);}


    @Override
    public List<Task_s> findbystuid(int stu_id) {
        return taskMapper.findbystuid(stu_id);
    }
    @Override
    public void updataTaskState(String passtaskid){taskMapper.updataTaskState(passtaskid);}


    @Override
    public List<Task> findTaskFor(int tutor_id,String task_type,String task_state){
        return taskMapper.findTaskFor(tutor_id,task_type,task_state);}
    @Override
    public List<Task> findtypeTaskFor(int tutor_id,String task_type){
        return taskMapper.findtypeTaskFor(tutor_id,task_type);}
    @Override
    public List<Task> findstateTaskFor(int tutor_id,String task_state){
        return taskMapper.findstateTaskFor(tutor_id,task_state);}

    @Override
    public List<Task> findmyallTaskFor(int tutor_id){return taskMapper.findmyallTaskFor(tutor_id);}

    @Override
    public List<Task> s_findTask(String task_type){return taskMapper.s_findTask(task_type);}


    @Override
    public List<Task> a_findTaskByTypeState(String task_type ,String task_state){
        return taskMapper.a_findTaskByTypeState(task_type,task_state);
    }
    @Override
    public List<Task>a_findTaskByType(String task_type){
        return taskMapper.a_findTaskByType(task_type);
    }
    @Override
    public List<Task> a_findTaskByState(String task_state){
        return taskMapper.a_findTaskByState(task_state);
    }
    @Override
    public  List<Task_s> a_findAppointStu(String task_id){return taskMapper.a_findAppointStu(task_id);}
    @Override
    public List<Task_s> a_findAppointStuid(int stu_id){return taskMapper.a_findAppointStuid(stu_id);}
    @Override
    public  List<Task_s> a_findAppointStuname(String stu_name){return taskMapper.a_findAppointStuname(stu_name);}
}

