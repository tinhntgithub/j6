package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.OrderDetailsDAO;
import dtn.asm.dao.OrdersDAO;
import dtn.asm.dao.ProductsDAO;
import dtn.asm.entity.OrderDetails;
import dtn.asm.entity.Orders;
import dtn.asm.entity.Products;
import dtn.asm.service.OrderDetailsService;
@Service
public class OrderDetailsServiceImp implements OrderDetailsService {

	@Autowired
	OrderDetailsDAO dao;
	@Autowired
	OrdersDAO daoOrder;
	
	@Override
	public List<OrderDetails> findAll() {
		return dao.findAll();
	}
	
	@Override
	public OrderDetails findById(Integer id) {
		return dao.findById(id).get();
	}
	
	@Override
	public void create(OrderDetails entity) {
		dao.save(entity);
	}
	@Override
	public void update(OrderDetails entity) {
		dao.save(entity);
	}
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}
	@Override
	public List<OrderDetails> getOrderDetails(Integer id) {
		return dao.findByOrdersId(daoOrder.findById(id).get());
	}
	@Override
	public Double getAmount(Integer id) {
		return dao.getAmount(id);
	}
	@Override
	public Double getTotal() {
		return dao.getTotal();
	}
	
	@Override
	public List<Products> getProduct(Integer id) {
		return dao.getAllProduct(id);
	}

}
