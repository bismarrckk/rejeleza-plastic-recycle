package com.production.v1.services;


import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.production.v1.model.Category;
import com.production.v1.repository.CategoryRepository;
import com.production.v1.web.dto.CategoryDTO;
import com.production.v1.web.dto.ProductsByCategory;

@Service
public class CategoryServiceImpl implements CategoryService{

	private CategoryRepository categoryRepo;
	
	public CategoryServiceImpl(CategoryRepository categoryRepo) {
		super();
		this.categoryRepo = categoryRepo;
	}

	@Override
	public void save(CategoryDTO categorydto) {
		Category category=new Category(categorydto.getName());
		categoryRepo.save(category);
		
	}

	@Override
	public Category getById(int id) {
		
		Optional<Category> optional=categoryRepo.findById(id);
		Category category=null;
		if(optional.isPresent()) {
			category=optional.get();
		}else {
			throw new RuntimeException("Category not found for id :"+ id);
		}
		return category;
	}

	@Override
	public Page<Category> getAllCategories(int pageNum,String sortField,String sortDir) {
		int pageSize=10;
		Pageable pageable=PageRequest.of(pageNum - 1, pageSize,sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
		return categoryRepo.findAll(pageable);
	}

	@Override
	public void update(CategoryDTO categorydto) {
		Category category=new Category(categorydto.getId(),categorydto.getName());
		categoryRepo.save(category);
		
	}

	@Override
	public void deleteCategory(int id) {
		categoryRepo.deleteById(id);
		
	}

	@Override
	public List<Category> getAllCategoriesDropdown() {
		return categoryRepo.findAll();
		
	}

	@Override
	public List<ProductsByCategory> getProductsByCategory(int id) {
		
		return categoryRepo.getProductsByCategory(id);
	}

}
