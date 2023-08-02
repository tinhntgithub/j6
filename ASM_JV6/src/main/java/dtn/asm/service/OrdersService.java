package dtn.asm.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import dtn.asm.entity.Orders;

public interface OrdersService {
	List<Orders> findAll();
	
	List<Orders> findOrderWait();
	
	List<Orders> findAllOrderCancel();
	
	List<Orders> findAllOrderDelivired();
	
	List<Orders> findAllOrderDone();

	List<Object[]> getRevenueByYear();


	Orders findById(Integer id);

	void create(Orders entity);

	void update(Orders entity);

	void delete(Integer id);
	
	Integer getCount();
}
