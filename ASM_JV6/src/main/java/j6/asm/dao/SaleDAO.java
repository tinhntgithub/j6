package j6.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import j6.asm.entity.Sale;

public interface SaleDAO extends JpaRepository<Sale, Integer> {

	@Query("SELECT count(o) FROM Sale o")
	Integer getCount();
	
	@Query("SELECT cp FROM Sale cp WHERE cp.code=?1")
	Sale findByCode(String code);
}
