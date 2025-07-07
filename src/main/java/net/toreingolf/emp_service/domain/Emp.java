package net.toreingolf.emp_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Emp {
    @Id
    private Long      empno;
    private String    ename;
    private String    job;
    private LocalDate hiredate;
    private Long      mgr;
    private Long      deptno;
}
