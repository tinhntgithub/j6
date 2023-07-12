package dtn.asm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.CartDAO;
import dtn.asm.entity.Accounts;
import dtn.asm.entity.Cart;
import dtn.asm.entity.Products;
import dtn.asm.service.CartService;
@Service
public class CartServiceImp implements CartService {

	@Autowired
	CartDAO dao;
	
	@Override
	public List<Cart> findAll() {
		return dao.findAll();
	}
	
	@Override
	public Cart findById(Integer id) {
		return dao.getById(id);
	}
	
	@Override
	public void create(Cart entity) {
		dao.save(entity);
	}
	
	@Override
	public void update(Cart entity) {
		dao.save(entity);
	}
	
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}
	
	public Optional<Cart> findByUsernameAndProduct(Accounts acc,Products pro) {
		return dao.findByUserCartAndProCart(acc,pro);
	}
	public List<Cart> findByUsername(Accounts acc) {
		return dao.findByUserCart(acc);
	}
}
