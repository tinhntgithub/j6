package j6.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.dao.FavoritesDAO;
import j6.asm.dao.OrderDetailsDAO;
import j6.asm.dao.OrdersDAO;
import j6.asm.dao.ProductColorDAO;
import j6.asm.dao.ProductImgDAO;
import j6.asm.dao.ProductsDAO;
import j6.asm.entity.Products;
import j6.asm.service.ProductsService;

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
	public List<Products> findAll() {
		return dao.findAll();
	}
	@Override
	public List<Products> findByCateId(Integer id) {
		return dao.listProduct_InCategories(id);
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
		return daoOrder.findByProductsId(pr).isEmpty() && daoFav.findByProductsId(pr).isEmpty();
	}

	@Override
	public Integer getCount() {
		return dao.getCount();
	}
}
