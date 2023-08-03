package j6.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import j6.asm.entity.ProductImg;

public interface ProductImgService {
	List<ProductImg> findAll();

	List<ProductImg> findByImgPro(Integer id);
	
	ProductImg findById(Integer id);

	void create(ProductImg entity);

	void update(ProductImg entity);

	void delete(Integer id);
}
