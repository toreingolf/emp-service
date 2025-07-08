package net.toreingolf.emp_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.toreingolf.emp_service.domain.Emp;
import net.toreingolf.emp_service.manager.EmpManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.stream.Stream;

import static net.toreingolf.emp_service.EmpTestUtils.BOND;
import static net.toreingolf.emp_service.EmpTestUtils.EMPNO_SCOTT;
import static net.toreingolf.emp_service.EmpTestUtils.SCOTT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmpControllerTest {

    private static final String URI = "/employees/";
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    EmpManager empManagerMock;

    @BeforeEach
    void setup() {
        when(empManagerMock.getEmp(EMPNO_SCOTT)).thenReturn(SCOTT);
    }

    @ParameterizedTest
    @MethodSource("empAndExpectedResult")
    void getEmp(Emp emp, ResultMatcher result) throws Exception {
        mockMvc.perform(get(URI + emp.getEmpno()))
                .andExpect(result);
    }

    @Test
    void getEmpList() throws Exception {
        mockMvc.perform(get(URI))
                .andExpect(status().isOk());
    }

    @Test
    void createEmp() throws Exception {
        when(empManagerMock.createEmp(any())).thenReturn(SCOTT);
        mockMvc.perform(post(URI)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(SCOTT)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("empAndExpectedResult")
    void updateEmp(Emp emp, ResultMatcher result) throws Exception {
        when(empManagerMock.updateEmp(any(), any())).thenReturn(emp);
        mockMvc.perform(put(URI + emp.getEmpno())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(emp)))
                .andExpect(result);
    }

    @ParameterizedTest
    @MethodSource("empAndExpectedResult")
    void deleteEmp(Emp emp, ResultMatcher result) throws Exception {
        mockMvc.perform(delete(URI + emp.getEmpno()))
                .andExpect(result);
        if (emp.getEmpno().equals(EMPNO_SCOTT)) {
            verify(empManagerMock).deleteEmp(emp.getEmpno());
        }
    }

    private static Stream<Arguments> empAndExpectedResult() {
        return Stream.of(
                Arguments.of(SCOTT, status().isOk()),
                Arguments.of(BOND, status().isNotFound())
        );
    }
}
