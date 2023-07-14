package j6.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import j6.asm.entity.Address;

public interface AddressService {
	List<Address> findAll();

	Address findById(Integer id);

	void create(Address entity);

	void update(Address entity);

	void delete(Integer id);
}
