package com.production.v1.web;



import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.production.v1.model.User;
import com.production.v1.model.UserRole;
import com.production.v1.services.UserRoleService;
import com.production.v1.services.UserService;
import com.production.v1.web.dto.UserRegistrationDto;


@Controller
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UserRoleService roleService;
	private UserService userService;
	public UsersController(UserService userService) {
		super();
		this.userService = userService;
	}
	private Log logger=LogFactory.getLog(RuntimeException.class);
	
	@GetMapping
	public String users(Model model) {
		return viewPage(model,1,"firstName","asc");
	}
	
	@GetMapping("/page/{pageNum}")
	public String viewPage(Model model,@PathVariable(name="pageNum") int pageNum, @Param("sortField") String sortField,
	        @Param("sortDir") String sortDir) {
		Page<User> page=userService.getAllUsers(pageNum,sortField,sortDir);
		List<User> listUsers=page.getContent();
		
		model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    
		model.addAttribute("listUsers",listUsers);
		return "admin/users";
	}
	
	
	
	/*private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}*/
	
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable(value="id") int id,Model model,RedirectAttributes attributes) {
		try {
		User user=userService.getUserById(id);
		List<UserRole> roles=roleService.getAllRoles();
		model.addAttribute("user",user);
		model.addAttribute("roles", roles);
		return "admin/edit_user";
		}catch(RuntimeException Ex) {
		 logger.error("Request  threw an exception ", Ex);
		 attributes.addFlashAttribute("message", "User not found!!");
		 return "redirect:/users";
		}
		
	}
	
	@PostMapping("/update")
	public String updateUser(@ModelAttribute("user")  UserRegistrationDto userDto,RedirectAttributes attribute ) {
		int id=userDto.getId();		
		try {
		userService.updateUser(id, userDto);
		attribute.addFlashAttribute("message", "Update successful!!");
		return"redirect:/users";
		}catch(RuntimeException Ex) {
		logger.error("Request  threw an exception ", Ex);
		attribute.addFlashAttribute("message", Ex);
		return"redirect:/users";
		}
	}
		
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable(value="id") int id,RedirectAttributes attributes) {
		try {
		userService.deleteUser(id);
		attributes.addFlashAttribute("message","User deleted!!");
		
		}catch(RuntimeException Ex) {
			logger.error("Request  threw an exception ", Ex);
			attributes.addFlashAttribute("message", "User not found!!");
		}
		return "redirect:/users";
	}
	


}
