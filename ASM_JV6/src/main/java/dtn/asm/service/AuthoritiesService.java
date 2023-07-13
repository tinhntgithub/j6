package dtn.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dtn.asm.entity.Accounts;
import dtn.asm.entity.Authorities;
import dtn.asm.entity.Roles;

public interface AuthoritiesService {
	List<Authorities> findAll();

	Authorities findById(Integer id);

	void create(Authorities entity);

	void update(Authorities entity);

	void delete(Integer id);
	
	Boolean checkAuth(Integer id);
	
	Boolean checkAuth2(String roleid,String user);
}
