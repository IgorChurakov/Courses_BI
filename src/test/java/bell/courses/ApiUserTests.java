package bell.courses;

import bell.courses.view.request.UserFilterView;
import bell.courses.view.request.UserSaveView;
import bell.courses.view.request.UserUpdateView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static bell.courses.ApiTestSuite.convertToJson;
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
        UserSaveView request = new UserSaveView();
        request.setOfficeId((long) 1);
        request.setFirstName("Test User");
        request.setPosition("Tester");

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/user/save").contentType(MediaType.APPLICATION_JSON).content(requestJson))
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
        UserSaveView request = new UserSaveView();
        request.setOfficeId((long) 1);
        request.setFirstName("Test Name");
        request.setPosition("Tester");
        request.setSecondName("Test Second Name");
        request.setMiddleName("Test Middle Name");
        request.setPhone("+7-965-63-95");
        request.setDocCode(21);
        request.setDocName("Russian Passport");
        request.setDocNumber("6315 865982");
        request.setDocDate("2015-12-23");
        request.setCitizenshipCode(643);
        request.setIsIdentified(true);

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/user/save").contentType(MediaType.APPLICATION_JSON).content(requestJson))
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
        UserFilterView request = new UserFilterView();
        request.setOfficeId((long)3);

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/user/list").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("4"))
                .andExpect(jsonPath("$.[0].position").value("Director"))
                .andExpect(jsonPath("$.[1].id").value("5"))
                .andExpect(jsonPath("$.[1].position").value("Advisor"));
    }

    @Test
    @Transactional
    public void simpleUpdateTest() throws Exception {
        UserUpdateView request = new UserUpdateView();
        request.setId((long) 3);
        request.setFirstName("Update Test");
        request.setPosition("Update Tester");

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/user/update").contentType(MediaType.APPLICATION_JSON).content(requestJson))
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
        UserUpdateView request = new UserUpdateView();
        request.setId((long) 4);
        request.setOfficeId((long) 1);
        request.setFirstName("UTest Name");
        request.setSecondName("UTest Second Name");
        request.setMiddleName("UTest Middle Name");
        request.setPosition("Update Tester");
        request.setPhone("+7-965-63-95");
        request.setDocName("Военный билет");
        request.setDocNumber("6319 41354138");
        request.setDocDate("2019-08-16");
        request.setCitizenshipCode(643);
        request.setIsIdentified(true);

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/user/update").contentType(MediaType.APPLICATION_JSON).content(requestJson))
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