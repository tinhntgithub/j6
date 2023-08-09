package j6.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import j6.asm.entity.ProductColor;

public interface ProductColorService {
	List<ProductColor> findAll();

	List<ProductColor> findByPd(Integer pdid);

	ProductColor findById(Integer id);

	ProductColor getOne(Integer id, Integer idd);

	void create(ProductColor entity);

	void update(ProductColor entity);

	void delete(Integer id);
	
	Boolean checkOrder(Integer id);
	
	Boolean checkExitsProduct(Integer id,Integer idd);

	List<ProductColor> getColorId(Integer id);

}
