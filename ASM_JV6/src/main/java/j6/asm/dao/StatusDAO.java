package j6.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import j6.asm.entity.Status;

public interface StatusDAO extends JpaRepository<Status, Integer> {


}
