package com.oracle.users;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.users.auth.TokenProvider;
import com.oracle.users.model.AuthToken;
import com.oracle.users.model.LoginUser;
import com.oracle.users.model.UserDto;
import com.oracle.users.model.entity.Role;
import com.oracle.users.model.entity.User;
import com.oracle.users.service.UserService;
import com.oracle.users.web.UserController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UsersApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTests {

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private TokenProvider jwtTokenUtil;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController controller;

	private static AuthToken authToken;
	private static AuthToken authTokenAdmin;

	@Autowired
	private WebApplicationContext context;

	protected MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		MockitoAnnotations.initMocks(this);

		authenticationManager = Mockito.mock(authenticationManager.getClass());
		jwtTokenUtil = Mockito.mock(jwtTokenUtil.getClass());
		userService = Mockito.mock(userService.getClass());

		ReflectionTestUtils.setField(controller, "authenticationManager", authenticationManager);
		ReflectionTestUtils.setField(controller, "jwtTokenUtil", jwtTokenUtil);
		ReflectionTestUtils.setField(controller, "userService", userService);

	}

	@Test
	public void testABSaveUser() throws Exception {

		String uri = "/users/register";

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri);
		request.header("Authorization", "Bearer " + authToken);

		UserDto user = new UserDto();
		user.setUsername("testUser");
		user.setName("testUser");
		user.setPassword("testUser");
		user.setPhone("testUser");
		user.setEmail("testUser");
		user.setIsAdmin(false);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

		request.content(mapper.writeValueAsString(user));

		MvcResult result = mockMvc.perform(request.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();

		User response = (User) mapper.readValue(content, User.class);
		Assert.assertNotNull(response);
		Role role = response.getRoles().stream().findAny().get();
		Assert.assertEquals("USER", role.getName());

	}

	@Test
	public void testACSaveAdmin() throws Exception {

		String uri = "/users/register";

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri);
		request.header("Authorization", "Bearer " + authToken);

		UserDto user = new UserDto();
		user.setUsername("testAdmin");
		user.setName("testAdmin");
		user.setPassword("testAdmin");
		user.setPhone("testAdmin");
		user.setEmail("testAdmin");
		user.setIsAdmin(true);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

		request.content(mapper.writeValueAsString(user));

		MvcResult result = mockMvc.perform(request.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();

		User response = (User) mapper.readValue(content, User.class);
		Assert.assertNotNull(response);
		Role role = response.getRoles().stream().filter(role1 -> "ADMIN".equals(role1.getName())).findAny().get();
		Assert.assertEquals("ADMIN", role.getName());

	}

	@Test
	public void testADGetByUserName() throws Exception {
		String uri = "/users/testAdmin";

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
		request.header("Authorization", "Bearer " + authToken);

		MvcResult result = mockMvc.perform(request.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

		User response = (User) mapper.readValue(content, User.class);

		Assert.assertNotNull(response);

	}

	@Test
	public void testAALoadGuestUser() throws Exception {
		ResponseEntity<?> token = controller.loadGuestUser();
		authToken = (AuthToken) token.getBody();
		Assert.assertNotNull(token);

	}

	@Test
	public void testAELogin() throws Exception {

		String uri = "/users/login";

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri);
		request.header("Authorization", "Bearer " + authToken);

		LoginUser user = new LoginUser();
		user.setUsername("testAdmin");
		user.setPassword("testAdmin");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

		request.content(mapper.writeValueAsString(user));

		MvcResult result = mockMvc.perform(request.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();

		authTokenAdmin = mapper.readValue(content, AuthToken.class);
		Assert.assertNotNull(authTokenAdmin);

	}

	@Test
	public void testAFGetUsers() throws Exception {
		try {
			String uri = "/users/";

			MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
			request.header("Authorization", "Bearer " + authToken);

			MvcResult result = mockMvc.perform(request.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			String content = result.getResponse().getContentAsString();

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			List<User> users = mapper.readValue(content, List.class);
			Assert.assertNotNull(users);
		} catch (Exception e) {
			
		}

	}

}
