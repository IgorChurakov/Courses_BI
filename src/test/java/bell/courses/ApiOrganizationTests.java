package bell.courses;

import bell.courses.view.request.OrganizationFilterView;
import bell.courses.view.request.OrganizationSaveView;
import bell.courses.view.request.OrganizationUpdateView;
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
public class ApiOrganizationTests {

    @Autowired
    MockMvc mvc;

    @Test
    @Transactional
    public void simpleListTest() throws Exception {
        OrganizationFilterView request = new OrganizationFilterView();
        request.setName("Wonder");

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/organization/list").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("2"))
                .andExpect(jsonPath("$.[0].name").value("World of Wonder"))
                .andExpect(jsonPath("$.[0].isActive").value("true"));
    }

    @Test
    @Transactional
    public void fullListTest() throws Exception {
        OrganizationFilterView request = new OrganizationFilterView();
        request.setName("net");
        request.setInn("24135846856461635384");
        request.setIsActive(true);

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/organization/list").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value("Planet Express"))
                .andExpect(jsonPath("$.[0].isActive").value("true"));
    }

    @Test
    @Transactional
    public void getTest() throws Exception {
        this.mvc.perform(get("/api/organization/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Planet Express"))
                .andExpect(jsonPath("$.fullName").value("Planet Express, Inc."))
                .andExpect(jsonPath("$.inn").value("24135846856461635384"))
                .andExpect(jsonPath("$.kpp").value("133769322"))
                .andExpect(jsonPath("$.address").value("USA, NNY St., New New York, Main st., 13"))
                .andExpect(jsonPath("$.phone").value("+79631596428"))
                .andExpect(jsonPath("$.isActive").value("true"));
    }

    @Test
    @Transactional
    public void simpleSaveTest() throws Exception {
        OrganizationSaveView request = new OrganizationSaveView();
        request.setName("Test Org");
        request.setFullName("Testers Org.");
        request.setInn("696969");
        request.setKpp("133769");
        request.setAddress("Test Town");

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/organization/save").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                    this.mvc.perform(get("/api/organization/3"))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id").value("3"))
                            .andExpect(jsonPath("$.name").value("Test Org"))
                            .andExpect(jsonPath("$.fullName").value("Testers Org."))
                            .andExpect(jsonPath("$.inn").value("696969"))
                            .andExpect(jsonPath("$.kpp").value("133769"))
                            .andExpect(jsonPath("$.address").value("Test Town"))
                            .andExpect(jsonPath("$.isActive").value("false"));
                });
    }

    @Test
    @Transactional
    public void fullSaveTest() throws Exception {
        OrganizationSaveView request = new OrganizationSaveView();
        request.setName("The Test Org");
        request.setFullName("The Testers Org.");
        request.setInn("69696969");
        request.setKpp("13376969");
        request.setAddress("The Test Town");
        request.setPhone("+7-965-632-47-89");
        request.setIsActive(true);

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/organization/save").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                    this.mvc.perform(get("/api/organization/4"))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id").value("4"))
                            .andExpect(jsonPath("$.name").value("The Test Org"))
                            .andExpect(jsonPath("$.fullName").value("The Testers Org."))
                            .andExpect(jsonPath("$.inn").value("69696969"))
                            .andExpect(jsonPath("$.kpp").value("13376969"))
                            .andExpect(jsonPath("$.address").value("The Test Town"))
                            .andExpect(jsonPath("$.phone").value("+7-965-632-47-89"))
                            .andExpect(jsonPath("$.isActive").value("true"));
                });
    }

    @Test
    @Transactional
    public void simpleUpdateTest() throws Exception {
        OrganizationUpdateView request = new OrganizationUpdateView();
        request.setId((long)2);
        request.setName("Test Org");
        request.setFullName("Testers Org.");
        request.setInn("696969");
        request.setKpp("133769");
        request.setAddress("Test Town");
        request.setIsActive(false);

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/organization/update").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                    this.mvc.perform(get("/api/organization/2"))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id").value("2"))
                            .andExpect(jsonPath("$.name").value("Test Org"))
                            .andExpect(jsonPath("$.fullName").value("Testers Org."))
                            .andExpect(jsonPath("$.inn").value("696969"))
                            .andExpect(jsonPath("$.kpp").value("133769"))
                            .andExpect(jsonPath("$.address").value("Test Town"))
                            .andExpect(jsonPath("$.phone").value("+79245613298"))
                            .andExpect(jsonPath("$.isActive").value("false"));
                });
    }

    @Test
    @Transactional
    public void fullUpdateTest() throws Exception {
        OrganizationUpdateView request = new OrganizationUpdateView();
        request.setId((long)1);
        request.setName("The Test Org");
        request.setFullName("The Testers Org.");
        request.setInn("69696969");
        request.setKpp("13376969");
        request.setAddress("The Test Town");
        request.setPhone("+7-965-632-47-89");
        request.setIsActive(true);

        String requestJson = convertToJson(request);

        this.mvc.perform(post("/api/organization/update").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(mvcResult -> {
                    this.mvc.perform(get("/api/organization/1"))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id").value("1"))
                            .andExpect(jsonPath("$.name").value("The Test Org"))
                            .andExpect(jsonPath("$.fullName").value("The Testers Org."))
                            .andExpect(jsonPath("$.inn").value("69696969"))
                            .andExpect(jsonPath("$.kpp").value("13376969"))
                            .andExpect(jsonPath("$.address").value("The Test Town"))
                            .andExpect(jsonPath("$.phone").value("+7-965-632-47-89"))
                            .andExpect(jsonPath("$.isActive").value("true"));
                });
    }
}
