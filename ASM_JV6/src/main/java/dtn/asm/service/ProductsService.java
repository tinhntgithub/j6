package dtn.asm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dtn.asm.entity.Products;

@Service
public interface ProductsService {
	List<Products> findAll();

	Page<Products> findAllPage(Pageable pageable);

	List<Products> findByCateId(Integer id);

	List<Products> findProductsInCategoryWithinPriceRange(Integer id, double min, double max);

	List<Object[]> findProductReport();

	Products findById(Integer id);

	void create(Products entity);

	void update(Products entity);

	void delete(Integer id);

	Boolean check(Integer id);

	Integer getCount();

	Page<Products> listProduct_InCategoriesPage(Integer integer, Pageable pageable);

	Page<Products> findAllProductsSortedByNameAZ(Pageable pageable);

	Page<Products> findAllProductsSortedByNameZA(Pageable pageable);

	Page<Products> findAllProductsSortedByPriceLowest(Pageable pageable);

	Page<Products> findAllProductsSortedByPriceHighest(Pageable pageable);

	Page<Products> findAllProductsSortedByNewest(Pageable pageable);

	Page<Products> findAllProductsSortedByOldest(Pageable pageable);

	Page<Products> findByCategoryAndPrice(int lowerPrice, int upperPrice, Pageable pageable);

}
