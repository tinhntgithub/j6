package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.OrdersDAO;
import dtn.asm.dao.SaleDAO;
import dtn.asm.entity.Sale;
import dtn.asm.service.SaleService;
@Service
public class SaleServiceImp implements SaleService {

	@Autowired
	SaleDAO dao;
	@Autowired
	OrdersDAO daoOrder;
	
	@Override
	public List<Sale> findAll() {
		return dao.findAll();
	}
	@Override
	public Sale findById(Integer id) {
		return dao.findById(id).get();
	}
	
	@Override
	public void create(Sale entity) {
		dao.save(entity);
	}
	@Override
	public void update(Sale entity) {
		dao.save(entity);
	}
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}
	
	@Override
	public Boolean checkOrder(Integer id) {
		return daoOrder.findBySaleId(this.findById(id)).isEmpty();
	}
	
	@Override
	public Integer getCount() {
		return dao.getCount();
	}

}
