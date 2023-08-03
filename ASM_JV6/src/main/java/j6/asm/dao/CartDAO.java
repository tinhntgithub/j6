package j6.asm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import j6.asm.entity.Accounts;
import j6.asm.entity.Cart;
import j6.asm.entity.Products;

public interface CartDAO extends JpaRepository<Cart, Integer> {
	Optional<Cart> findByUserCartAndProCart(Accounts userCart,Products pro);
	List<Cart> findByUserCart(Accounts userCart);
}
