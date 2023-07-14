package j6.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import j6.asm.entity.ProductImg;
import j6.asm.entity.Products;

public interface ProductImgDAO extends JpaRepository<ProductImg, Integer> {

	List<ProductImg> findByImgPro(Products imgPro);

}
