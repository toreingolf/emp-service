package net.toreingolf.emp_service;

import net.toreingolf.emp_service.domain.Emp;

public class EmpTestUtils {

    public static final Long EMPNO_BOND = 7L;
    public static final Long EMPNO_KING = 7839L;
    public static final Long EMPNO_SCOTT = 7788L;
    public static final Emp BOND = createEmp(EMPNO_BOND, "BOND");
    public static final Emp KING = createEmp(EMPNO_KING, "KING");
    public static final Emp SCOTT = createEmp(EMPNO_SCOTT, "SCOTT");

    private static Emp createEmp(Long empno, String ename) {
        Emp emp = new Emp();
        emp.setEmpno(empno);
        emp.setEname(ename);
        return emp;
    }
}
