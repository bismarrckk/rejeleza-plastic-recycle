package com.production.v1.services;



import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.production.v1.model.Product;

import com.production.v1.repository.ProductRepository;
import com.production.v1.web.dto.ProductDto;

@Service
public class ProductServiceImpl implements ProductService{
	private static final List<String> imageContentTypes = Arrays.asList("image/jpeg", "image/png");
	private static final List<String> videoContentTypes = Arrays.asList("video/mp4", "video/mkv");
	
	private ProductRepository productRepo;
	public ProductServiceImpl(ProductRepository producrRepo) {
		super();
		this.productRepo = producrRepo;
	}

	@Override
	public Product save(ProductDto productDto) {
		 	
		Product product=new Product(productDto.getTitle(),productDto.getCategory(),productDto.getCondition(),productDto.getQuantity(),productDto.getUnit(),productDto.getPrice(),productDto.getDescription(),productDto.getCounty(),
				productDto.getStreetAddress(),productDto.getCoverPhoto(),productDto.getVideo(),productDto.getPostDate(),productDto.getStatus(),productDto.getUser());
	
		return productRepo.save(product);
	}
	
	public Boolean validateImageFile(MultipartFile file) {
		String fileContentType = file.getContentType();
		if(imageContentTypes.contains(fileContentType)) {
	        return true;
	    }
		else {
			return false;
		}
	}
	public Boolean validateVideoFile(MultipartFile file) {
		String fileContentType = file.getContentType();
		if(videoContentTypes.contains(fileContentType)) {
	        return true;
	    }
		else {
			return false;
		}
	}

	@Override
	public Page<Product> getProductsByStatus(String status, int pageNum, String sortField, String sortDir) {
		int pageSize=10;
		Pageable pageable=PageRequest.of(pageNum - 1, pageSize,sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
		return productRepo.findByStatus(status, pageable);
	}

	@Override
	public Product getProductById(int id){
		Product product=null;
		Optional<Product> optional=productRepo.findById(id);
		if(optional.isPresent()) {
			product=optional.get();
		}else {
			throw new RuntimeException("Product not found!!");
		}
		return product;
		
	}

	@Override
	public void deleteProduct(int id){
		productRepo.deleteById(id);
		
	}

	@Override
	public void updateProduct(ProductDto productDto,int id) {
		Product product=getProductById(id);
		product.setStatus(productDto.getStatus());
		productRepo.save(product);
		
	}

	@Override
	public List<Product> getProductList() {
		return productRepo.findAll();
	}

	@Override
	public void approveProduct(int id) {
		Product product=productRepo.getById(id);
		product.setStatus("Approved");
		productRepo.save(product);
		
	}

	@Override
	public void declineProduct(int id) {
		Product product=productRepo.getById(id);
		product.setStatus("Declined");
		productRepo.save(product);
		
	}

	@Override
	public List<Product> getApprovedProducts(String status) {
		return productRepo.findByStatus(status);
	}

	


	



	


	
}
