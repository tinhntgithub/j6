package j6.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import j6.asm.entity.Roles;

public interface RolesDAO extends JpaRepository<Roles, String> {

}
