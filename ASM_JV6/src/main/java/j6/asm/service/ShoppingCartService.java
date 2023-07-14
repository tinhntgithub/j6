package j6.asm.service;

import java.util.List;

import j6.asm.entity.Accounts;
import j6.asm.entity.Cart;
import j6.asm.entity.Products;


public interface ShoppingCartService {
	void add(Integer id, Products pro, Integer qty,Accounts acc);

	void update(Cart cart);

	void clear();

	List<Cart> getCarts(Accounts acc);

	int getCount();

	double getAmount();

	void remove(Integer id);

}
