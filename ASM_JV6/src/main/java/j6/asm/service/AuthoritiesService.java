package j6.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import j6.asm.entity.Accounts;
import j6.asm.entity.Authorities;
import j6.asm.entity.Roles;

public interface AuthoritiesService {
	List<Authorities> findAll();

	Authorities findById(Integer id);

	void create(Authorities entity);

	void update(Authorities entity);

	void delete(Integer id);
	
	Boolean checkAuth(Integer id);
	
	Boolean checkAuth2(String roleid,String user);
}
