package bell.courses;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiUserTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    public void simpleSaveTest() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("officeId", "1");
        params.add("firstName", "Test User");
        params.add("position", "Tester");
        this.mvc.perform(post("/api/user/save").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                        this.mvc.perform(get("/api/user/7"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value("7"))
                                .andExpect(jsonPath("$.firstName").value("Test User"))
                                .andExpect(jsonPath("$.position").value("Tester"));
                });
    }

    @Test
    @Transactional
    public void fullSaveTest() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("officeId", "1");
        params.add("firstName", "Test Name");
        params.add("position", "Tester");
        params.add("secondName", "Test Second Name");
        params.add("middleName", "Test Middle Name");
        params.add("phone", "+7-965-63-95");
        params.add("docCode", "21");
        params.add("docName", "Russian Passport");
        params.add("docNumber", "6315 865982");
        params.add("docDate", "2015-12-23");
        params.add("citizenshipCode", "643");
        params.add("isIdentified", "true");
        this.mvc.perform(post("/api/user/save").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                        this.mvc.perform(get("/api/user/8"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value("8"))
                                .andExpect(jsonPath("$.firstName").value("Test Name"))
                                .andExpect(jsonPath("$.secondName").value("Test Second Name"))
                                .andExpect(jsonPath("$.middleName").value("Test Middle Name"))
                                .andExpect(jsonPath("$.position").value("Tester"))
                                .andExpect(jsonPath("$.phone").value("+7-965-63-95"))
                                .andExpect(jsonPath("$.docName").value("Паспорт гражданина Российской Федерации"))
                                .andExpect(jsonPath("$.docNumber").value("6315 865982"))
                                .andExpect(jsonPath("$.docDate").value("2015-12-23"))
                                .andExpect(jsonPath("$.citizenshipName").value("Russian Federation"))
                                .andExpect(jsonPath("$.citizenshipCode").value("643"))
                                .andExpect(jsonPath("$.isIdentified").value("true"));
                });
    }

    @Test
    @Transactional
    public void getTest() throws Exception {
        this.mvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.position").value("Director"));
    }

    @Test
    @Transactional
    public void listTest() throws Exception {
        this.mvc.perform(post("/api/user/list").param("officeId", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("4"))
                .andExpect(jsonPath("$.[0].position").value("Director"))
                .andExpect(jsonPath("$.[1].id").value("5"))
                .andExpect(jsonPath("$.[1].position").value("Advisor"));
    }

    @Test
    @Transactional
    public void simpleUpdateTest() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "3");
        params.add("firstName", "Update Test");
        params.add("position", "Update Tester");
        this.mvc.perform(post("/api/user/update").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                        this.mvc.perform(get("/api/user/3"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value("3"))
                                .andExpect(jsonPath("$.firstName").value("Update Test"))
                                .andExpect(jsonPath("$.position").value("Update Tester"));
        });
    }

    @Test
    @Transactional
    public void fullUpdateTest() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "4");
        params.add("officeId", "1");
        params.add("firstName", "UTest Name");
        params.add("secondName", "UTest Second Name");
        params.add("middleName", "UTest Middle Name");
        params.add("position", "Update Tester");
        params.add("phone", "+7-965-63-95");
        params.add("docName", "Военный билет");
        params.add("docNumber", "6319 41354138");
        params.add("docDate", "2019-08-16");
        params.add("citizenshipCode", "643");
        params.add("isIdentified", "true");
        this.mvc.perform(post("/api/user/update").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                        this.mvc.perform(get("/api/user/4"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value("4"))
                                .andExpect(jsonPath("$.firstName").value("UTest Name"))
                                .andExpect(jsonPath("$.secondName").value("UTest Second Name"))
                                .andExpect(jsonPath("$.middleName").value("UTest Middle Name"))
                                .andExpect(jsonPath("$.position").value("Update Tester"))
                                .andExpect(jsonPath("$.phone").value("+7-965-63-95"))
                                .andExpect(jsonPath("$.docName").value("Военный билет"))
                                .andExpect(jsonPath("$.docNumber").value("6319 41354138"))
                                .andExpect(jsonPath("$.docDate").value("2019-08-16"))
                                .andExpect(jsonPath("$.citizenshipName").value("Russian Federation"))
                                .andExpect(jsonPath("$.citizenshipCode").value("643"))
                                .andExpect(jsonPath("$.isIdentified").value("true"));
        });
    }
}