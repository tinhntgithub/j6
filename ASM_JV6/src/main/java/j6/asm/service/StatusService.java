package j6.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import j6.asm.entity.Status;

public interface StatusService {
	List<Status> findAll();

	Status findById(Integer id);

	void create(Status entity);

	void update(Status entity);

	void delete(Integer id);
}
