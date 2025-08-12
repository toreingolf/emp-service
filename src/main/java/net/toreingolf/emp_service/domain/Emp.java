package net.toreingolf.emp_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 10, message = "Last Name must be 1-10 characters")
    private String    ename;
    private String    job;
    private LocalDate hiredate;
    private Long      mgr;
    private Long      deptno;
}
