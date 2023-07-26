package dtn.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dtn.asm.entity.ProductImg;
import dtn.asm.entity.Products;

public interface ProductImgService {
	List<ProductImg> findAll();

	List<ProductImg> findByImgPro(Integer id);

	ProductImg findById(Integer id);

	void create(ProductImg entity);

	void update(ProductImg entity);

	void delete(Integer id);
}
