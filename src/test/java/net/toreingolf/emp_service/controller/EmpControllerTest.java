package net.toreingolf.emp_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.toreingolf.emp_service.manager.EmpManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static net.toreingolf.emp_service.EmpTestUtils.BOND;
import static net.toreingolf.emp_service.EmpTestUtils.EMPNO_BOND;
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
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    EmpManager empManagerMock;

    @Test
    void getEmp() throws Exception {
        when(empManagerMock.getEmp(EMPNO_SCOTT)).thenReturn(SCOTT);
        mockMvc.perform(get(URI + EMPNO_SCOTT))
                .andExpect(status().isOk());
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

    @Test
    void updateEmp() throws Exception {
        when(empManagerMock.getEmp(EMPNO_SCOTT)).thenReturn(SCOTT);
        when(empManagerMock.updateEmp(any(), any())).thenReturn(SCOTT);
        mockMvc.perform(put(URI + EMPNO_SCOTT)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(SCOTT)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEmp() throws Exception {
        when(empManagerMock.getEmp(EMPNO_BOND)).thenReturn(BOND);
        mockMvc.perform(delete(URI + EMPNO_BOND))
                .andExpect(status().isOk());
        verify(empManagerMock).deleteEmp(EMPNO_BOND);
    }
}
