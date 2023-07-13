package dtn.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dtn.asm.entity.Brand;
import dtn.asm.entity.Categories;
import dtn.asm.entity.Products;

public interface ProductsDAO extends JpaRepository<Products, Integer> {
		
	List<Products> findByCatePro(Categories catePro);
	
	List<Products> findByBrandPro(Brand brandPro);
	
	@Query (value = "select * from Products where CategoryId = ?1",nativeQuery = true)
	List<Products> listProduct_InCategories(int cate_id);
	
	@Query("SELECT count(o) FROM Products o WHERE o.avaliable = true")
	Integer getCount();
	
	
}
