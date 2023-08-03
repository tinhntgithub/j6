package j6.asm.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import j6.asm.entity.Accounts;
import j6.asm.entity.Address;

public interface AddressDAO extends JpaRepository<Address, Integer> {
	@Query("Select a from Address a where a.userAr=?1")
	List<Address> findByUsername(Accounts acc,Pageable page);
}
