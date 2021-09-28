package com.production.v1.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.production.v1.model.Category;
import com.production.v1.services.CategoryService;
import com.production.v1.web.dto.CategoryDTO;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}
	private Log logger=LogFactory.getLog(RuntimeException.class);
	@GetMapping
	public String categoryList(Model model) {
		return viewCategoryList(model,1,"name","asc");
	}

	@GetMapping("/page/{pageNum}")
	public String viewCategoryList(Model model,@PathVariable(name="pageNum") int pageNum, @Param("sortField") String sortField,
	        @Param("sortDir") String sortDir) {
		Page<Category> page=categoryService.getAllCategories(pageNum, sortField, sortDir);
		List<Category> categories=page.getContent();
		
		model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		
		model.addAttribute("listCategories", categories);
		return"admin/categories";
		
	}
	
	@GetMapping("/create")
	public String addCategory(Model model) {
		CategoryDTO categorydto=new CategoryDTO();
		model.addAttribute("category", categorydto);
		return "admin/add_category";
	}
	
	@PostMapping("/save")
	public String saveCategory(@ModelAttribute("category") CategoryDTO categorydto) {
		categoryService.save(categorydto);
		return "redirect:/categories";
		
	}
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable(value="id") int id,Model model,RedirectAttributes attributes) {
		try {
		Category category =categoryService.getById(id);
		model.addAttribute("category",category );
		return "admin/edit_category";
		}catch(RuntimeException Ex){
			logger.error("Request  threw an exception ", Ex);
			attributes.addFlashAttribute("mesage", "Category not found!!");
			return"redirect:/categories";
		}
		
		
	}
	@PostMapping("/update")
	public String updateCategory(@ModelAttribute("category") CategoryDTO categorydto) {
		categoryService.update(categorydto);
		return "redirect:/categories";
		
	}
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable("id") int id,RedirectAttributes attributes) {
		try {
	    categoryService.deleteCategory(id);
	    attributes.addFlashAttribute("message", "Category deleted!!");
	    }catch(RuntimeException Ex) {
	    	logger.error("Request threw an exception", Ex);
	    	attributes.addFlashAttribute("message","Category not found!!");
	    }
	    return "redirect:/categories";
	}
}
