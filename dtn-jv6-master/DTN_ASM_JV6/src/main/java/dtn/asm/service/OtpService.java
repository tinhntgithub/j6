package dtn.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dtn.asm.entity.Otp;

public interface OtpService {
	List<Otp> findAll();

	Otp findById(Integer id);

	void create(Otp entity);

	void update(Otp entity);

	void delete(Integer id);
}
