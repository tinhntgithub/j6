package j6.asm.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.entity.Accounts;
import j6.asm.entity.Cart;
import j6.asm.entity.Color;
import j6.asm.entity.ProductColor;
import j6.asm.entity.Products;
import j6.asm.service.CartService;
import j6.asm.service.ProductColorService;
import j6.asm.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	@Autowired
	CartService cartService;

	@Autowired
	ProductColorService pdclServ;

	@Override
	public void add(Integer id, Products pro, Integer qty, Accounts acc) {
		Optional<Cart> cart = cartService.findByUsernameAndProduct(acc, pro);
		Cart cart1 = new Cart();
		if (cart.isPresent()) {
			cart1 = cart.get();
			cart1.setQty(cart1.getQty() + qty);
			cartService.update(cart1);
		} else {
			// if (qty > 1) { // nếu sl > 1 thì mới có thể lưu (do quantity not null)
			// cart1.setQty(qty);
			// }

			if (qty < 1) {
				cart1.setQty(1);
			}
			cart1.setQty(qty);
			cart1.setProCart(pro);
			cart1.setColorCart(pro.getProductColors().get(0));
			cart1.setPrice(pro.getPrice() - (pro.getPrice() * pro.getSale() / 100));
			cart1.setUserCart(acc);
			cartService.create(cart1);
		}
	}

	@Override
	public void add2(Integer qty, Accounts acc, ProductColor productColor) {
		Optional<Cart> cart = cartService.findByUsernameAndProductColor(acc, productColor);
		Cart cart1 = new Cart();
		if (cart.isPresent()) {
			cart1 = cart.get();
			cart1.setQty(cart1.getQty() + qty);
			cartService.update(cart1);
		} else {
			if (qty > 1) {
				cart1.setQty(qty);
			}
			cart1.setProCart(productColor.getProducts());
			cart1.setColorCart(productColor);
			cart1.setPrice(productColor.getProducts().getPrice()
					- (productColor.getProducts().getPrice() * productColor.getProducts().getSale() / 100));
			cart1.setUserCart(acc);
			cartService.create(cart1);
		}
	}

	@Override
	public void remove(Integer id) {
		cartService.delete(id);
	}

	@Override
	public void update(Cart cart) {
		cartService.update(cart);
	}

	@Override
	public void clear() {
	}

	@Override
	public List<Cart> getCarts(Accounts acc) {
		return cartService.findByUsername(acc);
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public double getAmount() {
		return 0.0;
	}

}
