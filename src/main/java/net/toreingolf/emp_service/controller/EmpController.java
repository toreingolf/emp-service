package net.toreingolf.emp_service.controller;

import lombok.extern.slf4j.Slf4j;
import net.toreingolf.emp_service.domain.Emp;
import net.toreingolf.emp_service.manager.EmpManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
@Slf4j
public class EmpController {

    private final EmpManager empManager;

    EmpController(EmpManager empManager) {
        this.empManager = empManager;
    }

    @GetMapping("/")
    public ResponseEntity<List<Emp>> getEmpList() {
        log.info("get empList");
        return ResponseEntity.ok().body(empManager.getEmpList());
    }

    @GetMapping("/{empno}")
    public ResponseEntity<Emp> getEmp(@PathVariable("empno") Long empno) {
        log.info("get emp {}", empno);
        Emp emp = empManager.getEmp(empno);
        return emp == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(emp);
    }

    @PostMapping("/")
    public Emp createEmp(@RequestBody Emp emp) {
        log.info("create emp {}", emp);
        return empManager.createEmp(emp);
    }

    @PutMapping("/{empno}")
    public ResponseEntity<Emp> updateEmp(@PathVariable("empno") Long empno, @RequestBody Emp newEmp) {
        log.info("update emp {} with details from {}", empno, newEmp);
        Emp oldEmp = empManager.getEmp(empno);
        return oldEmp == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(empManager.updateEmp(oldEmp, newEmp));
    }

    @DeleteMapping("/{empno}")
    public ResponseEntity<Long> deleteEmp(@PathVariable("empno") Long empno) {
        log.info("delete emp {}", empno);
        Emp emp = empManager.getEmp(empno);
        return emp == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(empManager.deleteEmp(empno));
    }
}