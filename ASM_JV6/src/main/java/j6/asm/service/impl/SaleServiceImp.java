package j6.asm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.dao.OrdersDAO;
import j6.asm.dao.SaleDAO;
import j6.asm.entity.Sale;
import j6.asm.service.SaleService;
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
	@Override
	public Optional<List<Sale>> findByCode(String code) {
		return dao.findByCode(code);
	}

}
