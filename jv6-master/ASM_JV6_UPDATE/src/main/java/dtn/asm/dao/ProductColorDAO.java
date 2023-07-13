package dtn.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dtn.asm.entity.Color;
import dtn.asm.entity.ProductColor;
import dtn.asm.entity.Products;

public interface ProductColorDAO extends JpaRepository<ProductColor, Integer> {

	List<ProductColor> findByColor(Color color);
	
	List<ProductColor> findByProducts(Products products);
	
	@Query("SELECT o FROM ProductColor o WHERE o.color.id = ?1 AND o.products.id = ?2")
	List<ProductColor> getProductColors(Integer id,Integer idd);
}
