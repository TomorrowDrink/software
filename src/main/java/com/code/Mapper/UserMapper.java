package com.code.Mapper;

import com.code.Entity.Role;
import com.code.Entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by alison on 17-11-2.
 */

public interface UserMapper {


    List<User> getAll();

    User findByUsername(@Param("username") String username);

    User findById(@Param("id") Integer id);

    List<Role> findRolesByUsername(@Param("username") String username);

    void insertUser(User user);

    void insertSrole(@Param("user_id") Integer user_id);

    void insertTrole(@Param("user_id") Integer user_id);

    void insertArole(@Param("user_id") Integer user_id);

    void updatePwd(@Param("pwd") String pwd,@Param("newpwd") String newpwd);

}
