package com.production.v1.services;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Optional;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.production.v1.model.User;
import com.production.v1.model.UserRole;
import com.production.v1.repository.UserRepository;
import com.production.v1.repository.UserRoleRepository;
import com.production.v1.web.dto.UserRegistrationDto;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private UserRoleRepository roleRepo;
	
	private UserRepository userRepository;
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public void save(UserRegistrationDto userRegistrationDto,String siteURL)  {
		String randomCode=RandomString.make(64);
		User user=new User(userRegistrationDto.getFirstName(),userRegistrationDto.getLastName(),userRegistrationDto.getEmail(),userRegistrationDto.getPhone(),userRegistrationDto.getIdNumber(),passwordEncoder.encode(userRegistrationDto.getPassword()),randomCode,false);
		user.setRoles(Arrays.asList(roleRepo.findByName("MANAGER")));
		userRepository.save(user);
		try {
			sendVerificationEmail(user,siteURL);
		} catch (UnsupportedEncodingException | MessagingException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("Invalid username/password");
		}
		return new UserDetailsImpl(user);
	}

	
	public User findByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}
	
	private void sendVerificationEmail(User user, String siteURL)
	        throws MessagingException, UnsupportedEncodingException {
	    String toAddress = user.getEmail();
	    String fromAddress = "b77kibet@gmail.com";
	    String senderName = "IBIZZ SOLUTIONS";
	    String subject = "Please verify your registration";
	    String content = "Dear [[name]],<br>"
	            + "Please click the link below to verify your registration:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	            + "Thank you,<br>"
	            + "IBIZZ SOLUTIONS.";
	    
	   
	    MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	     
	    content = content.replace("[[name]]", user.getFirstName());
	    String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
	     
	    content = content.replace("[[URL]]", verifyURL);
	     
	    helper.setText(content, true);
	     
	    javaMailSender.send(message);
	     
	}

	@Override
	public Boolean verifyCode(String code) {
		User user=userRepository.findByVerificationCode(code);
		if (user == null || user.isEnabled()) {
	        return false;
	    } else {
	        user.setVerificationCode(null);
	        user.setEnabled(true);
	        userRepository.save(user);
	         
	        return true;
	    }
	}

	@Override
	public Page<User> getAllUsers(int pageNum,String sortField,String sortDir) {
		int pageSize=10;
		Pageable pageable=PageRequest.of(pageNum - 1, pageSize,sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
		return userRepository.findAll(pageable);
		
	}

	@Override
	public User getUserById(int id){
		// TODO Auto-generated method stub
		Optional<User> optional=userRepository.findById(id);
		User user=null;
		if(optional.isPresent()) {
			user=optional.get();
		}else {
			throw new RuntimeException();
		}
		return user;
	}

	@Override
	public void updateUser(int id,UserRegistrationDto userDto) {
		
		User user=getUserById(id);
		user.setEnabled(userDto.getEnabled());
		user.setRoles(userDto.getRoles());
		userRepository.save(user);
		
	}

	@Override
	public void deleteUser(int id) {
		
		userRepository.deleteById(id);
		
	
	}

	@Override
	public void updateResetPasswordToken(String token, String email) {
		User user=userRepository.findByEmail(email);
		if(user !=null ) {
			user.setResetPasswordToken(token);
			userRepository.save(user);
		}else {
			throw new RuntimeException("Email not found for address :"+ email+" !!");
		}
		
	}

	@Override
	public User getByResetPasswordToken(String token) {
		// TODO Auto-generated method stub
		return userRepository.findByResetPasswordToken(token);
	}

	@Override
	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
         
        user.setResetPasswordToken(null);
        userRepository.save(user);
		
	}
	
	public void sendResetEmail(String recipientEmail, String link) throws UnsupportedEncodingException, MessagingException{
		 MimeMessage message = javaMailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom("b77kibet@gmail.com", "IBIZZ SOLUTIONS");
	    helper.setTo(recipientEmail);
	     
	    String subject = "Here's the link to reset your password";
	     
	    String content = "<p>Hello,</p>"
	            + "<p>You have requested to reset your password.</p>"
	            + "<p>Click the link below to change your password:</p>"
	            + "<p><a href=\"" + link + "\">Change my password</a></p>"
	            + "<br>"
	            + "<p>Ignore this email if you do remember your password, "
	            + "or you have not made the request.</p>";
	     
	    helper.setSubject(subject);
	     
	    helper.setText(content, true);
	     
	    javaMailSender.send(message);
	}

	@Override
	public void updateProfile(int id, UserRegistrationDto userDto) {
		User user=getUserById(id);
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPhone(userDto.getPhone());
		user.setIdNumber(userDto.getIdNumber());
		
		userRepository.save(user);
	
	}

	@Override
	public void updatePassword(String pwd, String email) {
		User user=findByEmail(email);
		user.setPassword(pwd);
		userRepository.save(user);
		
		
	}


	
	

	
	

}
