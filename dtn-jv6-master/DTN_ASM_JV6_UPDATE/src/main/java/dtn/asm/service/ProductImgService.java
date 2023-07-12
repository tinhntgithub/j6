package dtn.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dtn.asm.entity.ProductImg;

public interface ProductImgService {
	List<ProductImg> findAll();

	ProductImg findById(Integer id);

	void create(ProductImg entity);

	void update(ProductImg entity);

	void delete(Integer id);
}
