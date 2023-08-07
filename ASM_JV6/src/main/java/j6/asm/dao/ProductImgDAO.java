package j6.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import j6.asm.entity.ProductImg;
import j6.asm.entity.Products;

public interface ProductImgDAO extends JpaRepository<ProductImg, Integer> {

	List<ProductImg> findByImgPro(Products imgPro);

	@Query (value = "select * from ProductImg where ProductId = ?1",nativeQuery = true)
	List<ProductImg> findByImgId(Integer id);

	@Query (value = "select * from ProductImg where ProductId = ?1",nativeQuery = true)
	List<ProductImg> findByPdid(Integer id);
	

}
