package dtn.asm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dtn.asm.entity.Brand;
import dtn.asm.entity.Categories;
import dtn.asm.entity.OrderDetails;
import dtn.asm.entity.Products;

public interface ProductsDAO extends JpaRepository<Products, Integer> {

	List<Products> findByCatePro(Categories catePro);

	List<Products> findByBrandPro(Brand brandPro);

	// Page<Products> findAllPage(Pageable pageable);
	@Query(value = "select * from Products where CategoryId = ?1", nativeQuery = true)
	Page<Products> listProduct_InCategoriesPage(int cate_id, Pageable pageable);

	@Query(value = "select * from Products where CategoryId = ?1", nativeQuery = true)
	List<Products> listProduct_InCategories(int cate_id);

	@Query("SELECT count(o) FROM Products o WHERE o.avaliable = true")
	Integer getCount();

	@Query("SELECT DISTINCT p FROM Products p " +
			"JOIN p.productColors pc " +
			"WHERE p.catePro.id = ?1 AND p.price BETWEEN ?2 AND ?3 AND pc.qty > 0")
	List<Products> findProductsInCategoryWithinPriceRange(int categoryId, double minPrice, double maxPrice);

	@Query("SELECT p.name, p.img, COUNT(od.productsId) AS NumberOfSales " +
			"FROM Products p " +
			"JOIN OrderDetails od ON od.productsId.id = p.id " +
			"JOIN Orders o ON o.id = od.ordersId.id " +
			"GROUP BY p.name, p.img ORDER BY NumberOfSales DESC")
	List<Object[]> findProductReport();

	@Query("SELECT p FROM Products p ORDER BY p.name ASC")
	Page<Products> findAllProductsSortedByNameAZ(Pageable pageable);

	@Query("SELECT p FROM Products p ORDER BY p.name DESC")
	Page<Products> findAllProductsSortedByNameZA(Pageable pageable);

	@Query("SELECT p FROM Products p ORDER BY p.price ASC")
	Page<Products> findAllProductsSortedByPriceLowest(Pageable pageable);

	@Query("SELECT p FROM Products p ORDER BY p.price DESC")
	Page<Products> findAllProductsSortedByPriceHighest(Pageable pageable);

	@Query("SELECT p FROM Products p ORDER BY p.date DESC")
	Page<Products> findAllProductsSortedByNewest(Pageable pageable);

	@Query("SELECT p FROM Products p ORDER BY p.date ASC")
	Page<Products> findAllProductsSortedByOldest(Pageable pageable);
	
	@Query("SELECT p FROM Products p " +
	"JOIN p.productColors c " + 
	"WHERE c.qty > 0 AND p.price >= ?1 AND p.price <= ?2")
Page<Products> findByCategoryAndPrice(int lowerPrice, int upperPrice, Pageable pageable);



}
