package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.StatusDAO;
import dtn.asm.entity.Status;
import dtn.asm.service.StatusService;
@Service
public class StatusServiceImp implements StatusService {

	@Autowired
	StatusDAO dao;
	
	@Override
	public List<Status> findAll() {
		return dao.findAll();
	}
	
	@Override
	public Status findById(Integer id) {
		return dao.getById(id);
	}
	
	@Override
	public void create(Status entity) {
		dao.save(entity);
	}
	
	@Override
	public void update(Status entity) {
		dao.save(entity);
	}
	
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

}
