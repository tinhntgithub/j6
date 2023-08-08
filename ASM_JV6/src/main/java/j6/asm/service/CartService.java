package j6.asm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import j6.asm.entity.Accounts;
import j6.asm.entity.Cart;
import j6.asm.entity.ProductColor;
import j6.asm.entity.Products;

public interface CartService {
	List<Cart> findAll();
	List<Cart> findByUsername(Accounts acc);

	Optional<Cart> findByUsernameAndProductColor(Accounts acc,ProductColor pro);
	Optional<Cart> findByUsernameAndProduct(Accounts acc,Products pro);

	Cart findById(Integer id);

	void create(Cart entity);

	void update(Cart entity);

	void delete(Integer id);
}
