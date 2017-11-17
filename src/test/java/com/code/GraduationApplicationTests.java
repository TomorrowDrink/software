package com.code;

import com.code.Entity.Role;
import com.code.Entity.User;
import com.code.Mapper.UserMapper;
import com.code.Service.UserService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GraduationApplicationTests {

	@Test
	public void contextLoads() {
	}
	@Autowired
	private UserService userService;
	@Autowired
	UserMapper userMapper;
	@Test
	@Rollback
	public void findByName() throws Exception {
		User u = userMapper.findByUsername("1512190420");
		u.setRoles(userMapper.findRolesByUsername("1512190420"));
//		Assert.assertEquals("1", u.getRoles());
		for(Role role:u.getRoles())
		{
			System.out.println(role.getRolename());
		}
		System.out.println();
		System.out.println(u.getUsername());
		System.out.println(u.getPassword());
		System.out.println(u.getName());



	}


}
