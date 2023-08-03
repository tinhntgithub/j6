package j6.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import j6.asm.entity.Roles;

public interface RolesService {
	List<Roles> findAll();

	Roles findById(String id);

	void create(Roles entity);

	void update(Roles entity);

	void delete(String id);
}
