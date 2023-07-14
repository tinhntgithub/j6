package j6.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import j6.asm.entity.Products;

@Service
public interface ProductsService {
	List<Products> findAll();
	
	List<Products> findByCateId(Integer id);

	Products findById(Integer id);

	void create(Products entity);

	void update(Products entity);

	void delete(Integer id);
	
	Boolean check(Integer id);
	
	Integer getCount();
}
