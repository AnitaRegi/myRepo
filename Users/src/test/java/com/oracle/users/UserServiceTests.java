package com.oracle.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.oracle.users.exception.UserServiceException;
import com.oracle.users.model.UserDto;
import com.oracle.users.model.entity.User;
import com.oracle.users.repo.UserRepository;
import com.oracle.users.service.RoleService;
import com.oracle.users.service.UserService;
import com.oracle.users.service.impl.UserServiceImpl;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UsersApplication.class )
public class UserServiceTests {

	@Autowired
	private UserService userService;

	@Mock
	RoleService roleService;
	@Mock
	UserRepository userRepo;
	@Mock
	BCryptPasswordEncoder bcryptEncoder;

	@Before
	public void init() {
		userService = new UserServiceImpl();
		MockitoAnnotations.openMocks(this);
		userRepo = Mockito.mock(userRepo.getClass());
		ReflectionTestUtils.setField(userService, "userRepo",userRepo);
		ReflectionTestUtils.setField(userService, "roleService",roleService);
		ReflectionTestUtils.setField(userService, "bcryptEncoder",bcryptEncoder);
	}
	
	@Test
	public void testFindOne(){
	
		User user = new User();
		user.setPassword("password");
		user.setUsername("Anita");
		
		Optional<User> userOpt = Optional.of(user);
		Mockito.when(userRepo.findByUsername("Anita")).thenReturn(userOpt);
		
		User userResponse=userService.findOne("Anita");
		
		Assert.assertEquals("testFindOne", user.getUsername(), userResponse.getUsername());


	}

	@Test
	public void testFindOneFailure() {
	
		User user = null ;
		
		Optional<User> userOpt = Optional.ofNullable(user);
		Mockito.when(userRepo.findByUsername("Anita")).thenReturn(userOpt);
		
		User userResponse=userService.findOne("Anita");	
		Assert.assertNull("testFindOne failure",  userResponse);

	}
	
	@Test
	public void testFindAllNoData() {
		
		Mockito.when(userRepo.findAll()).thenReturn(new ArrayList<>());
		List<User> users = userService.findAll();	
		Assert.assertNotNull(users);
		Assert.assertEquals("testFindAll No Data", 0, users.size());
	}
	
	@Test
	public void testFindAllSuccess() {
		User user1 = new User();
		user1.setPassword("password");
		user1.setUsername("Anita");
		
		User user2 = new User();
		user2.setPassword("password");
		user2.setUsername("Anita");
		
		
		List<User> mockUsers = new ArrayList<>();
		mockUsers.add(user1); mockUsers.add(user2);
		Mockito.when(userRepo.findAll()).thenReturn(mockUsers);
		List<User> users = userService.findAll();	
		Assert.assertNotNull("testFindAll Success",  users);

	}
	
	
	@Test
	public void testSaveUserNameAlreadyExists() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUsername("Anita");
		userDto.setPassword("Password");
		userDto.setEmail("someone@somemail.com");
		userDto.setName("Anita Regi");
		userDto.setPhone("0987654321");
		userDto.setIsAdmin(true);
		
		User expectedUser = userDto.getUserFromDto();
		
	     Mockito.when(userRepo.findByUsername("Anita")).thenReturn(Optional.of(expectedUser));
	     try {
	     userService.save(userDto);
	     }catch (UserServiceException e) {
			Assert.assertEquals("Username Already exists!", e.getErrorMessage());
		}

	}

}