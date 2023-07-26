package dtn.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dtn.asm.entity.ProductColor;
import dtn.asm.entity.ProductImg;

public interface ProductColorService {
	List<ProductColor> findAll();

	List<ProductColor> getColorId(Integer id);

	ProductColor findById(Integer id);

	void create(ProductColor entity);

	void update(ProductColor entity);

	void delete(Integer id);
	
	Boolean checkOrder(Integer id);
	
	Boolean checkExitsProduct(Integer id,Integer idd);

}
