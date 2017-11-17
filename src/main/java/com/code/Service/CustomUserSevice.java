package com.code.Service;

import com.code.Entity.Role;
import com.code.Entity.User;
import com.code.Mapper.UserMapper;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alison on 17-11-4.
 */
@Service
public class CustomUserSevice implements UserDetailsService {

    @Autowired
    UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userMapper.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        user.setRoles(userMapper.findRolesByUsername(username));

        System.out.println("username:"+user.getUsername()+";password:"+user.getPassword());

        for(Role role:user.getRoles())
        {
            System.out.println(role.getRolename());
        }
        return user;
    }


}
