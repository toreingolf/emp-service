package net.toreingolf.emp_service.manager;

import net.toreingolf.emp_service.persistence.EmpRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static net.toreingolf.emp_service.EmpTestUtils.EMPNO_BOND;
import static net.toreingolf.emp_service.EmpTestUtils.EMPNO_KING;
import static net.toreingolf.emp_service.EmpTestUtils.EMPNO_SCOTT;
import static net.toreingolf.emp_service.EmpTestUtils.KING;
import static net.toreingolf.emp_service.EmpTestUtils.SCOTT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmpManagerTest {

    private static final EmpRepo empRepoMock = mock(EmpRepo.class);

    private final EmpManager empManager = new EmpManager(empRepoMock);

    @BeforeAll
    static void setup() {
        when(empRepoMock.findById(eq(EMPNO_SCOTT))).thenReturn(Optional.of(SCOTT));
        when(empRepoMock.findAll()).thenReturn(List.of(SCOTT, KING));
    }

    @Test
    void getEmp() {
        var emp = empManager.getEmp(EMPNO_SCOTT);
        assertThat(emp).isEqualTo(SCOTT);
    }

    @Test
    void getEmpList() {
        var empList = empManager.getEmpList();
        assertThat(empList.size()).isEqualTo(2);
    }

    @Test
    void createEmp() {
        empManager.createEmp(SCOTT);
        verify(empRepoMock).save(eq(SCOTT));
    }

    @Test
    void updateEmp() {
        when(empRepoMock.save(any())).thenReturn(KING);
        var emp = empManager.updateEmp(KING, SCOTT);
        assertThat(emp.getEmpno()).isEqualTo(EMPNO_KING);
    }

    @Test
    void deleteEmp() {
        var empno = empManager.deleteEmp(EMPNO_BOND);
        assertThat(empno).isEqualTo(EMPNO_BOND);
    }
}
