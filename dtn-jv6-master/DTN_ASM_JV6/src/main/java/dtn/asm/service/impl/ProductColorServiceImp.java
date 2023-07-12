package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.OrderDetailsDAO;
import dtn.asm.dao.ProductColorDAO;
import dtn.asm.entity.Color;
import dtn.asm.entity.ProductColor;
import dtn.asm.service.OrderDetailsService;
import dtn.asm.service.ProductColorService;
@Service
public class ProductColorServiceImp implements ProductColorService {

	@Autowired
	ProductColorDAO dao;
	@Autowired
	OrderDetailsDAO daoOrder;
	
	@Override
	public List<ProductColor> findAll() {
		return dao.findAll();
	}
	
	@Override
	public ProductColor findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public void create(ProductColor entity) {
		dao.save(entity);
	}
	
	@Override
	public void update(ProductColor entity) {
		dao.save(entity);
	}
	
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}
	@Override
	public Boolean checkOrder(Integer id) {
		return daoOrder.findByColorId(this.findById(id)).isEmpty();
	}
	
	@Override
	public Boolean checkExitsProduct(Integer id, Integer idd) {
		return !dao.getProductColors(id, idd).isEmpty();
	}
}
