package com.code.Service.lmpl;

import com.code.Entity.Role;
import com.code.Entity.User;
import com.code.Service.UserService;
import com.code.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by alison on 17-10-29.
 */
@Service
public class UserServicelmpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUserList() {
        return userMapper.getAll();
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public List<User> findUsernameByRole(Integer rid) {
        return userMapper.findUsernameByRole(rid);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    public List<Role> findRolesByUsername(String username) {
        return userMapper.findRolesByUsername(username);
    }

    @Override
    public void insertSrole(Integer user_id) {
        userMapper.insertSrole(user_id);
    }
    @Override
    public void insertTrole(Integer user_id) {
        userMapper.insertTrole(user_id);
    }

    @Override
    public void insertArole(Integer user_id) {
        userMapper.insertArole(user_id);
    }

    @Override
    public void updatePwd(String pwd, String newpwd) {
        userMapper.updatePwd(pwd,newpwd);
    }
}
