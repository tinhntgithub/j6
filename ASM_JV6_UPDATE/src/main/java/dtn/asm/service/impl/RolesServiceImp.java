package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.RolesDAO;
import dtn.asm.entity.Roles;
import dtn.asm.service.RolesService;

@Service
public class RolesServiceImp implements RolesService {

	@Autowired
	RolesDAO dao;

	@Override
	public List<Roles> findAll() {
		return dao.findAll();
	}

	@Override
	public Roles findById(String id) {
		return dao.findById(id).get();
	}

	@Override
	public void create(Roles entity) {
		dao.save(entity);
	}

	@Override
	public void update(Roles entity) {
		dao.save(entity);
	}

	@Override
	public void delete(String id) {
		dao.deleteById(id);
	}

}
