package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.AddressDAO;
import dtn.asm.entity.Address;
import dtn.asm.service.AddressService;
@Service
public class AddressServiceImp implements AddressService {

	@Autowired
	AddressDAO dao;
	
	@Override
	public List<Address> findAll() {
		return dao.findAll();
	}
	
	@Override
	public Address findById(Integer id) {
		return dao.getById(id);
	}
	
	@Override
	public void create(Address entity) {
		dao.save(entity);
	}
	
	@Override
	public void update(Address entity) {
		dao.save(entity);
	}
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

}
