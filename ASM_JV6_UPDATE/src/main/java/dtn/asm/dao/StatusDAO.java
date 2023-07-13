package dtn.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dtn.asm.entity.Status;

public interface StatusDAO extends JpaRepository<Status, Integer> {


}
