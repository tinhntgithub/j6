package dtn.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dtn.asm.entity.Accounts;
import dtn.asm.entity.Authorities;
import dtn.asm.entity.Roles;

public interface AuthoritiesDAO extends JpaRepository<Authorities, Integer> {

	List<Authorities> findByUserAuthor(Accounts userAuthor);

	List<Authorities> findByRoleId(Roles roleId);

	@Query("SELECT o FROM Authorities o WHERE o.roleId.id = ?1 AND o.userAuthor.username = ?2")
	List<Authorities> getAccountAuth(String id, String user);

	@Query(value = "select roleid from Authorities where username = ?1", nativeQuery = true)
	List<String> findRoleByUsername(String username);
}
