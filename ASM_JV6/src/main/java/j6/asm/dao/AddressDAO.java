package j6.asm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import j6.asm.entity.Accounts;
import j6.asm.entity.Address;

public interface AddressDAO extends JpaRepository<Address, Integer> {
	@Query("Select a from Address a where a.userAr=?1")
	List<Address> findByUsername(Accounts acc,Pageable page);

	@Query("Select a from Address a where a.userAr.username=?1")
	Optional<List<Address>> findByUsername (String username);

	@Query("SELECT o FROM Address o where o.id=?1 and o.userAr.username = ?2")
	Optional<Address> findContactIdByUserAr(Integer id,String userAr);
}
