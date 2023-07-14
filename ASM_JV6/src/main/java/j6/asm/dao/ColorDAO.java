package j6.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import j6.asm.entity.Color;

public interface ColorDAO extends JpaRepository<Color, Integer> {

	@Query("SELECT count(o) FROM Color o")
	Integer getCount();
	
}
