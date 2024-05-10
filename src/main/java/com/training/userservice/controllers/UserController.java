package com.training.userservice.controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.userservice.dao.User;

@RestController
public class UserController {

	List<User> userlist = new ArrayList<User>();
	
	public UserController() {
		userlist.add(new User(1, "vivek", "Hyd", "vivek@gmail.com"));
		userlist.add(new User(2, "anand", "Bang", "anand@gmail.com"));
		userlist.add(new User(3, "ryan", "NY", "ryan@gmail.com"));
		userlist.add(new User(4, "bob", "chennai", "bob@gmail.com"));
		userlist.add(new User(5, "melisa", "chicago", "melisa@gmail.com"));
	}
	
	@RequestMapping("/greet")
	public String greet() {
		return "Hello From UserService";
	}
	
	@RequestMapping(value="/users",method = RequestMethod.GET)
	public List<User> getUsers(){
		return userlist;
	}
	
	@RequestMapping("/user/{uid}")
	public User getUserbyId(@PathVariable int uid) {	
		return userlist.stream().filter(usr -> usr.getUserId() == uid).findAny().orElse(null);
	}
	
	@RequestMapping("/user")
	public User getUserbyIdrequestparam(@RequestParam int uid) {
		return userlist.stream().filter(usr -> usr.getUserId() == uid).findAny().orElse(null);
	}
	
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public User saveUser(@RequestBody User user) {
		userlist.add(user);
		return userlist.stream().filter(usr -> usr.getUserId() == user.getUserId()).findAny().orElse(null);
	}
	
	@RequestMapping(value = "/update/{userid}",method = RequestMethod.PUT)
	public User updateEntireUser(@PathVariable int userid,@RequestBody User user) {
		User existingUser= userlist.stream()
				.filter(usr -> usr.getUserId() == userid).findAny().orElse(null);
		
		
		if(existingUser!=null) {
			existingUser.setUserName(user.getUserName());
			existingUser.setAddr(user.getAddr());
			existingUser.setEmail(user.getEmail());
		}
		
		return existingUser;
	}
	
	
	@RequestMapping(value="/updateuser/{userid}",method = RequestMethod.PATCH)
	public User updateUser(@PathVariable int userid,@RequestBody User user) {
		User existingUser= userlist.stream()
				.filter(usr -> usr.getUserId() == userid).findAny().orElse(null);
		
		if(existingUser!=null) {
			existingUser.setUserName(user.getUserName()!=null?user.getUserName():existingUser.getUserName());
			existingUser.setAddr(user.getAddr()!=null?user.getAddr():existingUser.getAddr());
			existingUser.setEmail(user.getEmail()!=null?user.getEmail():existingUser.getEmail());
		}
		return existingUser;
	}
	
	@RequestMapping(value = "/updatefeild/{userId}", method = RequestMethod.PATCH)
	public User updateUserFeild(@PathVariable Integer userId, @RequestBody Map<String, String> feildMap) {
		User existingUser = userlist.stream().filter(usr -> usr.getUserId() == userId).findAny().orElse(null);

		Set<String> feildSet = feildMap.keySet();
		
		// Regular for loop
		for (String userFeild : feildSet) {
			Field field = ReflectionUtils.findField(User.class, userFeild);
			field.setAccessible(true);
			ReflectionUtils.setField(field, existingUser, feildMap.get(userFeild));
		}

		// Stream api
		feildMap.forEach((k, v) -> {
			Field field = ReflectionUtils.findField(User.class, k);
			field.setAccessible(true);
			ReflectionUtils.setField(field, existingUser, v);
		});

		return existingUser;
	}
	
	
	
	
	@RequestMapping(value = "/delete/{userid}",method = RequestMethod.DELETE)
	public String deleteuser(@PathVariable int userid) {
		User existingUser = userlist.stream().filter(usr -> usr.getUserId() == userid).findAny().orElse(null);
		if(userlist.remove(existingUser)) {
			return "User Deleted with UserID :: "+userid; 
		}
		return "User Not Avilable";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
