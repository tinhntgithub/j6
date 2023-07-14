package j6.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.dao.StatusDAO;
import j6.asm.entity.Status;
import j6.asm.service.StatusService;
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
