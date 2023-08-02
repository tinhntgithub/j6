package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dtn.asm.dao.FavoritesDAO;
import dtn.asm.dao.OrderDetailsDAO;
import dtn.asm.dao.OrdersDAO;
import dtn.asm.dao.ProductColorDAO;
import dtn.asm.dao.ProductImgDAO;
import dtn.asm.dao.ProductsDAO;
import dtn.asm.entity.Products;
import dtn.asm.service.ProductsService;

@Service
public class ProductsServiceImp implements ProductsService {

	@Autowired
	ProductsDAO dao;
	@Autowired
	OrderDetailsDAO daoOrder;
	@Autowired
	ProductColorDAO daoPrC;
	@Autowired
	ProductImgDAO daoPrI;
	@Autowired
	FavoritesDAO daoFav;

	@Override
	public Page<Products> findAllProductsSortedByNameAZ(Pageable page) {
		return dao.findAllProductsSortedByNameAZ(page);
	}

	@Override
	public Page<Products> findAllProductsSortedByNameZA(Pageable page) {
		return dao.findAllProductsSortedByNameZA(page);
	}

	@Override
	public Page<Products> findAllProductsSortedByPriceLowest(Pageable page) {
		return dao.findAllProductsSortedByPriceLowest(page);
	}

	@Override
	public Page<Products> findAllProductsSortedByPriceHighest(Pageable page) {
		return dao.findAllProductsSortedByPriceHighest(page);
	}

	@Override
	public Page<Products> findAllProductsSortedByNewest(Pageable page) {
		return dao.findAllProductsSortedByNewest(page);
	}

	@Override
	public Page<Products> findAllProductsSortedByOldest(Pageable page) {
		return dao.findAllProductsSortedByOldest(page);
	}

	@Override
	public Page<Products> findByCategoryAndPrice(int lowerPrice, int upperPrice, Pageable pageable) {
		return dao.findByCategoryAndPrice(lowerPrice,upperPrice, pageable);
	}

	@Override
	public List<Products> findAll() {
		return dao.findAll();
	}

	@Override
	public Page<Products> findAllPage(Pageable page) {
		return dao.findAll(page);
	}

	@Override
	public List<Products> findByCateId(Integer id) {
		return dao.listProduct_InCategories(id);
	}

	@Override
	public Page<Products> listProduct_InCategoriesPage(Integer id, Pageable page) {
		return dao.listProduct_InCategoriesPage(id, page);
	}

	@Override
	public List<Object[]> findProductReport() {
		return dao.findProductReport();
	}

	@Override
	public List<Products> findProductsInCategoryWithinPriceRange(Integer id, double min, double max) {
		return dao.findProductsInCategoryWithinPriceRange(id, min, max);
	}

	@Override
	public Products findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public void create(Products entity) {
		dao.save(entity);
	}

	@Override
	public void update(Products entity) {
		dao.save(entity);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Boolean check(Integer id) {
		Products pr = this.findById(id);
		return daoOrder.findByProductsId(pr).isEmpty() && daoPrC.findByProducts(pr).isEmpty()
				&& daoPrI.findByImgPro(pr).isEmpty() && daoFav.findByProductsId(pr).isEmpty();
	}

	@Override
	public Integer getCount() {
		return dao.getCount();
	}
}
