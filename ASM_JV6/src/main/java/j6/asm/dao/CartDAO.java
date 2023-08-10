package j6.asm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import j6.asm.entity.Accounts;
import j6.asm.entity.Cart;
import j6.asm.entity.ProductColor;
import j6.asm.entity.Products;

public interface CartDAO extends JpaRepository<Cart, Integer> {
	Optional<Cart> findByUserCartAndProCart(Accounts userCart,Products pro);
	List<Cart> findByUserCart(Accounts userCart);

	@Query (value ="select * from Cart where Username = ?1 and Color = ?2 ", nativeQuery = true)
	Optional<Cart> findByUsernameAndProductColor(Accounts acc,ProductColor pro);
}
