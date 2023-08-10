package j6.asm.service;

import java.util.List;

import j6.asm.entity.Accounts;
import j6.asm.entity.Cart;
import j6.asm.entity.Color;
import j6.asm.entity.ProductColor;
import j6.asm.entity.Products;


public interface ShoppingCartService {
	void add(Integer id, Products pro, Integer qty,Accounts acc);

	void add2(Integer qty, Accounts acc, ProductColor productColor);

	void update(Cart cart);

	void clear();

	List<Cart> getCarts(Accounts acc);

	int getCount();

	double getAmount();

	void remove(Integer id);

}
