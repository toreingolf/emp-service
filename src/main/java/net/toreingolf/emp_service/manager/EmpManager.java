package net.toreingolf.emp_service.manager;

import lombok.extern.slf4j.Slf4j;
import net.toreingolf.emp_service.domain.Emp;
import net.toreingolf.emp_service.persistence.EmpRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmpManager {

    private final EmpRepo empRepo;

    public EmpManager(EmpRepo empRepo) {
        this.empRepo = empRepo;
    }

    public List<Emp> getEmpList() {
        log.info("get empList");
        return empRepo.findAll(Sort.by(Sort.Direction.ASC, "ename"));
    }

    public Emp getEmp(Long empno) {
        log.info("get emp {}", empno);
        return empRepo.findById(empno)
                .orElse(null);
    }

    public Emp createEmp(Emp emp) {
        log.info("create emp {}", emp);
        return empRepo.save(emp);
    }

    public Emp updateEmp(Emp oldEmp, Emp newEmp) {
        log.info("update emp {} with details from {}", oldEmp, newEmp);
        oldEmp.setEname(newEmp.getEname());
        oldEmp.setJob(newEmp.getJob());
        oldEmp.setHiredate(newEmp.getHiredate());
        oldEmp.setMgr(newEmp.getMgr());
        oldEmp.setDeptno(newEmp.getDeptno());
        return empRepo.save(oldEmp);
    }

    public Long deleteEmp(Long empno) {
        log.info("delete emp {}", empno);
        empRepo.deleteById(empno);
        return empno;
    }
}
