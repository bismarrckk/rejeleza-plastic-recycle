package com.production.v1.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.production.v1.model.Category;
import com.production.v1.model.Product;
import com.production.v1.model.User;
import com.production.v1.services.CategoryService;
import com.production.v1.services.ProductService;
import com.production.v1.services.UserService;
import com.production.v1.util.FileUploadUtil;
import com.production.v1.web.dto.ProductDto;
import com.production.v1.web.dto.SearchProduct;
import com.production.v1.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;	
	private Log logger=LogFactory.getLog(RuntimeException.class);
	
	@GetMapping
	public String index(Model model,@Param("q") String q) {
		if(q != null) {
		List<SearchProduct> products=productService.searchProduct(q);
		model.addAttribute("products", products);
		return"auth_index";
		}
		List<Product> products=productService.getApprovedProducts("Approved");
		model.addAttribute("products", products);
		return"auth_index";
	}
	@GetMapping("/create")
	public String showProductForm(Model model) {		
		model.addAttribute("categories",categoryService.getAllCategoriesDropdown());
		return "add_products";
	}
	
	@PostMapping("/save")
	public String saveProduct(@RequestParam("coverPhoto") MultipartFile imageFile,@RequestParam("video") MultipartFile videoFile,HttpServletRequest request,RedirectAttributes attributes) throws IOException {
		
		if(!productService.validateImageFile(imageFile)) {
			attributes.addFlashAttribute("message", "Invalid image file type,Allowed file types are jpeg,jpg,png");
			return "redirect:/products/create";
		}
		if(!productService.validateVideoFile(videoFile)) {
			attributes.addFlashAttribute("message", "Invalid video file type,Allowed file types are mp4,mkv");
			return "redirect:/products/create";
		}
		
		Principal principal = request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.findByEmail(email);
		
		String imageFileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        String videoFileName = StringUtils.cleanPath(videoFile.getOriginalFilename());
        
        String cleanImageFileName=imageFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
        String cleanVideoFileName=videoFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
        
        String title=request.getParameter("title");
        String condition=request.getParameter("condition");
        String county=request.getParameter("county");
        String description=request.getParameter("description");
        Date date=new Date();
        Double price=Double.parseDouble(request.getParameter("price"));
        Double quantity=Double.parseDouble(request.getParameter("quantity"));
        String streetAddress=request.getParameter("streetAddress");
        String unit=request.getParameter("unit");
        String category=request.getParameter("category");
        int categoryId=Integer.parseInt(category);
        
        Category cat=categoryService.getById(categoryId);
         
        String titleCaps=WordUtils.capitalize(title);
        ProductDto product=new ProductDto();
        product.setCoverPhoto(cleanImageFileName);
        product.setVideo(cleanVideoFileName);
        product.setTitle(titleCaps);
        product.setCategory(cat);
        product.setCondition(condition);
        String countyCaps=WordUtils.capitalize(county);
        product.setCounty(countyCaps);
        product.setDescription(description);
        product.setPostDate(date);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setStatus("Pending");
        product.setStreetAddress(streetAddress);
        product.setUnit(unit);
        product.setUser(user);
               
		productService.save(product);
		
		Path currentPath= Paths.get(".");
	    Path absolutePath=currentPath.toAbsolutePath();
	    String finalPath=absolutePath + "/src/main/resources/static/photos/";
		String uploadDir=finalPath;
		
		FileUploadUtil.saveFile(uploadDir, cleanImageFileName, imageFile);
       
        FileUploadUtil.saveFile(uploadDir, cleanVideoFileName, videoFile);
		
		return "redirect:/products?success";
		
	}
	
	@GetMapping("/approved")
	public String getApprovedProducts(Model model) {
		return viewProducts(model,1,"title","asc","Approved");
	}
	@GetMapping("/declined")
	public String getDeclinedProducts(Model model) {
		return viewProducts(model,1,"title","asc","Declined");
	}
	@GetMapping("/pending")
	public String getPendingProducts(Model model) {
		return viewProducts(model,1,"title","asc","Pending");
	}
	
	@GetMapping("/page/{pageNum}")
	public String viewProducts(Model model,@PathVariable(name="pageNum") int pageNum, @Param("sortField") String sortField,
	        @Param("sortDir") String sortDir,@Param("status") String status) {
		Page<Product> page=productService.getProductsByStatus(status,pageNum,sortField,sortDir);
		List<Product> listProducts=page.getContent();
		
		model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    
		model.addAttribute("listProducts",listProducts);
		return "admin/products";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(value="id") int id,RedirectAttributes attributes) {
		Path currentPath= Paths.get(".");
	    Path absolutePath=currentPath.toAbsolutePath();
	    String finalPath=absolutePath + "/src/main/resources/static/photos/";
		
		try {
			Product product=productService.getProductById(id);
			String path1=product.getCoverPhoto();
			String path2=product.getVideo();
			
			String finalPathh1=finalPath+path1;
			String finalPathh2=finalPath+path2;
			
			Path deletePath1=Paths.get(finalPathh1);
			Path deletePath2=Paths.get(finalPathh2);
			
			Files.delete(deletePath1);
			Files.delete(deletePath2);
			
			productService.deleteProduct(id);
			attributes.addFlashAttribute("message","Product deleted!!");
			
		}catch(RuntimeException | IOException Ex) {
			logger.error("Request  threw an exception ", Ex);
			attributes.addFlashAttribute("message", "Product no found!!");
		}
			
		return"redirect:/products/approved";
		
	}
	
	@GetMapping("/edit/{id}")
	public String editProductStatus(@PathVariable(value="id") int id,RedirectAttributes attributes,Model model) {
		try {
			Product product=productService.getProductById(id);
			model.addAttribute("product", product);
			return"admin/edit_product";
		}catch(RuntimeException Ex) {
			attributes.addFlashAttribute("message", "Product not found!!");
			return "redirect:/products/list";
		}	
		
	}
	
	@PostMapping("/confirm")
	public String confirmPostStatus(HttpServletRequest request) {
		String productId=request.getParameter("id");
		String approve=request.getParameter("approve");
		String decline=request.getParameter("decline");
		int id=Integer.parseInt(productId);
		if(approve !=null) {
			productService.approveProduct(id);
		}
		if(decline !=null) {
			productService.declineProduct(id);	
		}
		return "redirect:/products";
		
		
	}
	
	@GetMapping("/show/{id}")
	public String showDetails(@PathVariable(value="id") int id,Model model,RedirectAttributes attributes) {
		try {
			Product product=productService.getProductById(id);
			model.addAttribute("product", product);
			return "product_details";
		}
		catch(RuntimeException Ex) {
			attributes.addFlashAttribute("message", "Product not found!!");
			return "redirect:/products";
		}
	}
	
	@GetMapping("/profile")
	public String showUpdateProfileForm(HttpServletRequest request,Model model) {
		Principal principal = request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.findByEmail(email);
		model.addAttribute("user", user);
		return"profile";
	}

	@PostMapping("/profile")
	public String updateProfile(@Valid @ModelAttribute("user")  UserRegistrationDto userRegistrationDto, BindingResult bindingResult,RedirectAttributes attributes) {
		if(bindingResult.hasErrors()) {
			return "profile";
		}
		int id=userRegistrationDto.getId();
		userService.updateProfile(id, userRegistrationDto);
		attributes.addFlashAttribute("message", "Profile updated successfully!!");
		return "redirect:/products";
	}

	@PostMapping("/password")
	public String changePassword(HttpServletRequest request,RedirectAttributes attributes) {
		Principal principal=request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.findByEmail(email);
		String registeredPwd=user.getPassword();
		
		String currentPwd=request.getParameter("currentPwd");		
		String newPwd=request.getParameter("newPwd");
		String confirmPwd=request.getParameter("confirmPwd");
		
		if((passwordEncoder.matches(currentPwd, registeredPwd))&&(newPwd.equals(confirmPwd))) {
			
				String newPassword=passwordEncoder.encode(confirmPwd);
				userService.updatePassword(newPassword, email);
				
			
			attributes.addFlashAttribute("message", "Password reset successful,Login with your new password!!");
			return "redirect:/login";
		}
		else {
		attributes.addFlashAttribute("message", "Passwords do not match!!");
		return "redirect:/products/profile";
		}
	}
	
	@GetMapping("/myOffers")
	public String showMyOffers(Model model,HttpServletRequest request) {
		Principal principal=request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.findByEmail(email);
		List<Product> products=productService.getOffersByUser(user);
		model.addAttribute("productList", products);
		return"my_products";
		
	}
	
}
