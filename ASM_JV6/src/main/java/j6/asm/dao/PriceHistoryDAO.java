package j6.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import j6.asm.entity.PriceHistory;

public interface PriceHistoryDAO extends JpaRepository<PriceHistory, Integer> {

	@Query("SELECT max(o.id) FROM PriceHistory o")
	Integer getPriceNew();
	
}
