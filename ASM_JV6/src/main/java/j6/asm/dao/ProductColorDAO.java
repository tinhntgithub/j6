package j6.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import j6.asm.entity.Color;
import j6.asm.entity.ProductColor;
import j6.asm.entity.Products;

public interface ProductColorDAO extends JpaRepository<ProductColor, Integer> {

	List<ProductColor> findByColor(Color color);
	
	List<ProductColor> findByProducts(Products products);
	
	@Query("SELECT o FROM ProductColor o WHERE o.color.id = ?1 AND o.products.id = ?2")
	List<ProductColor> getProductColors(Integer id,Integer idd);

	@Query (value = "select * from ProductColor where ProductId = ?1",nativeQuery = true)
	List<ProductColor> getColorId(int id);
	

}
