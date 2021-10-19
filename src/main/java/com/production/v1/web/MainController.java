package com.production.v1.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.production.v1.model.User;
import com.production.v1.services.CategoryService;
import com.production.v1.services.ProductService;
import com.production.v1.services.UserService;
import com.production.v1.util.BaseUrlUtility;
import com.production.v1.web.dto.ProductsByCategory;
import com.production.v1.web.dto.UserRegistrationDto;

import net.bytebuddy.utility.RandomString;


@Controller
public class MainController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	//constructor based injection
	private UserService userService;
	public MainController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("/")
	public String home(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("MANAGER"))) {
		return "auth_index";
		}
		
		model.addAttribute("products", productService.getLatestProducts());
		model.addAttribute("categories",categoryService.getAllCategoriesDropdown());
		return "index";
	}
	
	@GetMapping("/show/{id}")
	public String showProductsByCategory(@PathVariable(value="id") int id,Model model,RedirectAttributes attributes) {
			try {
			List<ProductsByCategory> cat=categoryService.getProductsByCategory(id);
			model.addAttribute("productList", cat);
			model.addAttribute("categories",categoryService.getAllCategoriesDropdown());
			model.addAttribute("categoryName",categoryService.getById(id));
			
			return "product_list";
			}catch(RuntimeException Ex) {
				attributes.addFlashAttribute("message","Product in this Category not found!!");
				return "product_list";
			}
		
		
	}

	
	@GetMapping("/contact")
	public String contactUs(Model model) {
		model.addAttribute("categories",categoryService.getAllCategoriesDropdown());
		return "contact";
	}	
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("categories",categoryService.getAllCategoriesDropdown());
		return "login";
	}
	@GetMapping("/register")
	//create model attribute to bind data
	public String showRegistrationForm(Model model) {
		UserRegistrationDto userRegistrationDto=new UserRegistrationDto();
		model.addAttribute("user", userRegistrationDto);
		return "register";
	}
	
	@PostMapping("/save")
	public String registerUserAccount(@Valid @ModelAttribute("user")  UserRegistrationDto userRegistrationDto, BindingResult bindingResult,HttpServletRequest request) {
		User existing = userService.findByEmail(userRegistrationDto.getEmail());
		if (existing != null) {
            bindingResult.rejectValue("email", null, "There is an account already registered with the same email");
        }
		
		if(bindingResult.hasErrors()) {
			return "register";
		}
		
		String url = BaseUrlUtility.getSiteURL(request);
		userService.save(userRegistrationDto,url);
		return "redirect:/register?success";
		
	}
		
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
	    if (userService.verifyCode(code)) {
	        return "redirect:/login?verify_success";
	    } else {
	    	return "redirect:/login?verify_error";
	    }
	}
	
	@GetMapping("/forgot-password")
	public String showForgotPasswordForm(){
		return "forgot_password";
	}
	@PostMapping("/forgot-password")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
	    String token = RandomString.make(30);
	     
	    try {
	        userService.updateResetPasswordToken(token, email);
	        String resetPasswordLink =BaseUrlUtility.getSiteURL(request) + "/reset-password?token=" + token;
	        userService.sendResetEmail(email, resetPasswordLink);
	        model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
	         
	    }
	    catch (RuntimeException ex) {
	        model.addAttribute("error", ex.getMessage());
	        }
	    catch (UnsupportedEncodingException | MessagingException e) {
	        model.addAttribute("error", "Error while sending email");
	    }
	         
	    return "forgot_password";
	}
	
	@GetMapping("/reset-password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
	    User user = userService.getByResetPasswordToken(token);
	    
	    model.addAttribute("token", token);
	     
	    if (user == null) {
	        model.addAttribute("message", "Invalid Token");
	        return "password_reset_form";
	    }
	     
	    return "password_reset_form";
	}
	
	@PostMapping("/reset-password")
	public String processResetPassword(HttpServletRequest request, Model model) {
	    String token = request.getParameter("token");
	    String password = request.getParameter("password");
	     
	    User user = userService.getByResetPasswordToken(token);
	   	     
	    if (user == null) {
	        model.addAttribute("message", "Invalid Token");
	        return "password_reset_form";
	    } else {           
	        userService.updatePassword(user, password);
	         
	    }
	     
	    return "redirect:/login?password_reset_success";
	}
	

}
