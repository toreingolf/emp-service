package net.toreingolf.emp_service.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.toreingolf.emp_service.domain.Emp;
import net.toreingolf.emp_service.manager.EmpManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
@Slf4j
public class EmpController {

    private final EmpManager empManager;

    EmpController(EmpManager empManager) {
        this.empManager = empManager;
    }

    @GetMapping
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

    @PostMapping
    public Emp createEmp(@RequestBody @Valid Emp emp) {
        log.info("create emp {}", emp);
        return empManager.createEmp(emp);
    }

    @PutMapping("/{empno}")
    public ResponseEntity<Emp> updateEmp(@PathVariable("empno") Long empno, @RequestBody @Valid Emp newEmp) {
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}