package dtn.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dtn.asm.entity.ProductImg;
import dtn.asm.entity.Products;

public interface ProductImgDAO extends JpaRepository<ProductImg, Integer> {

	List<ProductImg> findByImgPro(Products imgPro);

}
