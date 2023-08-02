package dtn.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dtn.asm.entity.Accounts;
import dtn.asm.entity.Favorites;
import dtn.asm.entity.Products;

public interface FavoritesDAO extends JpaRepository<Favorites, Integer> {

	List<Favorites> findByProductsId(Products productsId);

	List<Favorites> findByUserFvr(Accounts userFvr);

	@Query("SELECT o FROM Favorites o WHERE o.userFvr.username=:username AND o.productsId.id=:productsID")
	Favorites findByProductsIdAndUsername(String username, Integer productsID);

	@Query("SELECT p.name, p.img, COUNT(f.productsId) AS CountLike FROM Favorites f " +
        "JOIN Products p ON p.id = f.productsId " +
        "GROUP BY p.name, p.img ORDER BY CountLike DESC")
	List<Object[]> listReportFavorites();

}
