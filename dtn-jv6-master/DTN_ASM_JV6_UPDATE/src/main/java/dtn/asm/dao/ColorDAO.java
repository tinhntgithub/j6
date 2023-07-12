package dtn.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dtn.asm.entity.Color;

public interface ColorDAO extends JpaRepository<Color, Integer> {

	@Query("SELECT count(o) FROM Color o")
	Integer getCount();
	
}
