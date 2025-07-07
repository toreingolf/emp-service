package net.toreingolf.emp_service.persistence;

import net.toreingolf.emp_service.domain.Emp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepo extends JpaRepository<Emp, Long> {}