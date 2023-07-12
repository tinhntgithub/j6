package dtn.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dtn.asm.entity.Address;

public interface AddressService {
	List<Address> findAll();

	Address findById(Integer id);

	void create(Address entity);

	void update(Address entity);

	void delete(Integer id);
}
