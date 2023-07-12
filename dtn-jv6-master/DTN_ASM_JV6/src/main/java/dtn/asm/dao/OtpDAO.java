package dtn.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dtn.asm.entity.Otp;

public interface OtpDAO extends JpaRepository<Otp, Integer> {

}
