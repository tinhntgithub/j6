package j6.asm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import j6.asm.entity.Address;

public interface AddressService {
	List<Address> findAll();
	Optional<List<Address>> findByUsername (String username);
	Address findById(Integer id);

	void create(Address entity);

	void update(Address entity);

	void delete(Integer id);
	
	Optional<List<Address>> findByUsername(String username);
}
