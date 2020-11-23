package centene.github.io.centene_enrollee.rest;

import centene.github.io.centene_enrollee.config.CustomNotFoundException;
import centene.github.io.centene_enrollee.model.DependentDTO;
import centene.github.io.centene_enrollee.model.EnrolleeDTO;
import centene.github.io.centene_enrollee.service.EnrolleeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnrolleeController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EnrolleeControllerTest {
    private static final String BASE_URL = "/api/enrollees";

    @MockBean
    EnrolleeService enrolleeService;
    @Autowired
    private MockMvc mockMvc;

    private List<EnrolleeDTO> enrolleeDTOS = new ArrayList<>();

    @Before
    public void initEnrollees() {
        EnrolleeDTO enrolleeDTO = new EnrolleeDTO();
        enrolleeDTO.setId(1L);
        enrolleeDTO.setIsActive(true);
        enrolleeDTO.setTelephone("9782278567");
        enrolleeDTO.setName("Venky");
        enrolleeDTO.setBirthDate(LocalDate.of(1997, 4, 17));

        DependentDTO dependentDTO = new DependentDTO();
        dependentDTO.setBirthDate(LocalDate.of(1998, 12, 12));
        dependentDTO.setName("Cameron");
        dependentDTO.setId(1L);

        enrolleeDTO.getDependents().add(dependentDTO);
        enrolleeDTOS.add(enrolleeDTO);


        EnrolleeDTO enrolleeReq = new EnrolleeDTO();
        enrolleeReq.setIsActive(true);
        enrolleeReq.setTelephone("9782278557");
        enrolleeReq.setName("James");
        enrolleeReq.setBirthDate(LocalDate.of(1996, 4, 17));

        enrolleeDTOS.add(enrolleeReq);

        EnrolleeDTO enrolleeRes = new EnrolleeDTO();
        enrolleeRes.setId(2L);
        enrolleeRes.setIsActive(true);
        enrolleeRes.setTelephone("9782278557");
        enrolleeRes.setName("James");
        enrolleeRes.setBirthDate(LocalDate.of(1996, 4, 17));

        enrolleeDTOS.add(enrolleeRes);
    }

    @Test
    public void testGetEnrolleeSuccess() throws Exception {
        when(enrolleeService.get(1L)).thenReturn(enrolleeDTOS.get(0));
        this.mockMvc.perform(get(BASE_URL+"/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Venky"))
                .andReturn();
    }


    @Test(expected = NestedServletException.class)
    public void testGetEnrolleeNotFound() throws Exception {
            when(enrolleeService.get(-1L)).thenThrow(new CustomNotFoundException());
            this.mockMvc.perform(get(BASE_URL+"/-1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPostEnrolleeSuccess() throws Exception {
        when(enrolleeService.create(enrolleeDTOS.get(1))).thenReturn(enrolleeDTOS.get(2));

        this.mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON).content(asJsonString(enrolleeDTOS.get(1))))
                .andExpect(status().isCreated());
    }


    @Test
    public void testPostEnrollFailure() throws Exception {
        EnrolleeDTO enrollee = enrolleeDTOS.get(1);
        enrollee.setBirthDate(null);
        enrollee.setName(null);
        this.mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON).content(asJsonString(enrollee)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testPostDependentSuccess() throws Exception {
        DependentDTO dependentDTO = new DependentDTO();
        dependentDTO.setId(2L);
        dependentDTO.setName("James");
        dependentDTO.setBirthDate(LocalDate.of(1997, 2, 27));
        enrolleeDTOS.get(0).getDependents().add(dependentDTO);
        dependentDTO.setId(null);
        when(enrolleeService.addDependent(1L, dependentDTO))
                .thenReturn(enrolleeDTOS.get(0));

        this.mockMvc.perform(post(BASE_URL+"/1/dependents")
                .contentType(APPLICATION_JSON).content(asJsonString(dependentDTO)))
                .andExpect(status().isCreated());
    }
}