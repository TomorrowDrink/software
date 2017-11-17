package com.code.Service;

import com.code.Entity.Role;
import com.code.Entity.User;

import java.util.List;

/**
 * Created by alison on 17-10-29.
 */
public interface UserService  {
    public List<User> getUserList();
    public User findUserById(Integer id);
    public User findUserByUsername(String username);
    public void insertUser(User user);
    public List<Role> findRolesByUsername(String username);
    public void insertSrole(Integer user_id);
    public void insertTrole(Integer user_id);
    public void insertArole(Integer user_id);
    void updatePwd(String pwd, String newpwd);

}
