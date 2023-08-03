package j6.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.dao.OrderDetailsDAO;
import j6.asm.dao.ProductColorDAO;
import j6.asm.entity.Color;
import j6.asm.entity.ProductColor;
import j6.asm.service.OrderDetailsService;
import j6.asm.service.ProductColorService;
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

	@Override
	public List<ProductColor> getColorId(Integer id) {
		return dao.getColorId(id);
	}
}
