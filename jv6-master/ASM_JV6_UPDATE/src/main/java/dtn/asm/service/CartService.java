package dtn.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dtn.asm.entity.Cart;

public interface CartService {
	List<Cart> findAll();

	Cart findById(Integer id);

	void create(Cart entity);

	void update(Cart entity);

	void delete(Integer id);
}
