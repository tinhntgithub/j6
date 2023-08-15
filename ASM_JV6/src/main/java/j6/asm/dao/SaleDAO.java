package j6.asm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import j6.asm.entity.Sale;

public interface SaleDAO extends JpaRepository<Sale, Integer> {

	@Query("SELECT count(o) FROM Sale o")
	Integer getCount();
<<<<<<< HEAD

	@Query("Select o from Sale o where code = ?1")
	Optional<List<Sale>> findByCode(String code);

	@Query(value = "UPDATE Sale set amountused = amountused + 1 WHERE id = ?1", nativeQuery = true)
	void updateQuantityByCode(@Param("code") Integer code);

	@Query(value = "select * from Sale where GETDATE() between createDate and endDate ORDER BY amountused ASC;", nativeQuery = true)
	List<Sale> findCurrentSale();
=======
	
	@Query("SELECT cp FROM Sale cp WHERE cp.code=?1")
	Sale findByCode(String code);
>>>>>>> duylk
}
