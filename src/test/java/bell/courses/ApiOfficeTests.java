package bell.courses;

import bell.courses.view.request.OfficeFilterView;
import bell.courses.view.request.OfficeSaveView;
import bell.courses.view.request.OfficeUpdateView;
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
public class ApiOfficeTests {

    @Autowired
    MockMvc mvc;


    @Test
    @Transactional
    public void listTest() throws Exception {
        OfficeFilterView request = new OfficeFilterView();
        request.setOrgId((long)2);

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/office/list").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("3"))
                .andExpect(jsonPath("$.[0].name").value("Office of Fantasies"))
                .andExpect(jsonPath("$.[0].isActive").value("true"));
    }

    @Test
    @Transactional
    public void getTest() throws Exception {
        this.mvc.perform(get("/api/office/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("The secretary room"))
                .andExpect(jsonPath("$.address").value("USA, NNY St., New New York, Main st., 13"))
                .andExpect(jsonPath("$.phone").value("+79642135456"))
                .andExpect(jsonPath("$.isActive").value("true"));
    }

    @Test
    @Transactional
    public void simpleSaveTest() throws Exception {
        OfficeSaveView request = new OfficeSaveView();
        request.setOrgId((long)2);
        request.setName("Testers Office");
        request.setAddress("Test st.");

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/office/save").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                    this.mvc.perform(get("/api/office/5"))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id").value("5"))
                            .andExpect(jsonPath("$.name").value("Testers Office"))
                            .andExpect(jsonPath("$.address").value("Test st."))
                            .andExpect(jsonPath("$.isActive").value("false"));
                });
    }

    @Test
    @Transactional
    public void fullSaveTest() throws Exception {
        OfficeSaveView request = new OfficeSaveView();
        request.setOrgId((long)2);
        request.setName("The Testers Office");
        request.setAddress("Test st., Test Town");
        request.setPhone("+7-962-56-62-632");
        request.setIsActive(true);

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/office/save").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                    this.mvc.perform(get("/api/office/6"))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id").value("6"))
                            .andExpect(jsonPath("$.name").value("The Testers Office"))
                            .andExpect(jsonPath("$.address").value("Test st., Test Town"))
                            .andExpect(jsonPath("$.phone").value("+7-962-56-62-632"))
                            .andExpect(jsonPath("$.isActive").value("true"));
                });
    }

    @Test
    @Transactional
    public void simpleUpdateTest() throws Exception {
        OfficeUpdateView request = new OfficeUpdateView();
        request.setId((long)2);
        request.setName("Testers Office");
        request.setAddress("Test st.");

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/office/update").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                    this.mvc.perform(get("/api/office/2"))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id").value("2"))
                            .andExpect(jsonPath("$.name").value("Testers Office"))
                            .andExpect(jsonPath("$.phone").value("+79642135456"))
                            .andExpect(jsonPath("$.address").value("Test st."))
                            .andExpect(jsonPath("$.isActive").value("true"));
                });
    }

    @Test
    @Transactional
    public void fullUpdateTest() throws Exception {
        OfficeUpdateView request = new OfficeUpdateView();
        request.setId((long)3);
        request.setName("The Testers Office");
        request.setAddress("Test st., Test Town");
        request.setPhone("+7-962-56-62-632");
        request.setIsActive(false);

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/office/update").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                    this.mvc.perform(get("/api/office/3"))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id").value("3"))
                            .andExpect(jsonPath("$.name").value("The Testers Office"))
                            .andExpect(jsonPath("$.address").value("Test st., Test Town"))
                            .andExpect(jsonPath("$.phone").value("+7-962-56-62-632"))
                            .andExpect(jsonPath("$.isActive").value("false"));
                });
    }
}
