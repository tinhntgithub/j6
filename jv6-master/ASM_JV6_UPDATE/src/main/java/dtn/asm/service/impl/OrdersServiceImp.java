package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.OrdersDAO;
import dtn.asm.entity.Orders;
import dtn.asm.service.OrdersService;
@Service
public class OrdersServiceImp implements OrdersService {

	@Autowired
	OrdersDAO dao;
	
	@Override
	public List<Orders> findAll() {
		return dao.findAll();
	}
	
	@Override
	public List<Orders> findAllOrderCancel() {
		return dao.findAllById2(4);
	}
	
	@Override
	public List<Orders> findAllOrderDone() {
		return dao.findAllById2(3);
	}
	
	@Override
	public List<Orders> findAllOrderDelivired() {
		return dao.findAllById2(2);
	}
	
	@Override
	public List<Orders> findOrderWait() {
		return dao.findAllById2(1);
	}
	
	@Override
	public Orders findById(Integer id) {
		return dao.findById(id).get();
	}
	
	@Override
	public void create(Orders entity) {
		dao.save(entity);
	}
	
	@Override
	public void update(Orders entity) {
		dao.save(entity);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}
	
	@Override
	public Integer getCount() {
		return dao.getCount();
	}
}
