package j6.asm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.dao.AddressDAO;
import j6.asm.entity.Accounts;
import j6.asm.entity.Address;
import j6.asm.service.AddressService;
@Service
public class AddressServiceImp implements AddressService {

	@Autowired
	AddressDAO dao;
		@Override
	public Optional<List<Address>> findByUsername(String username) {
		return dao.findByUsername(username);
	}
	
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

	@Override
	public Optional<List<Address>> findByUsername(String username) {
		return dao.findByUsername(username);
	}

}
