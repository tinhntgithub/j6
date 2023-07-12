package dtn.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dtn.asm.entity.Brand;

public interface BrandDAO extends JpaRepository<Brand, Integer> {

	@Query("SELECT count(o) FROM Brand o")
	Integer getCount();
	
}
