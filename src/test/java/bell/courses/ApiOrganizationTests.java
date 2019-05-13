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
public class ApiOrganizationTests {

    @Autowired
    MockMvc mvc;

    @Test
    @Transactional
    public void simpleListTest() throws Exception {
        LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("name","Wonder");
        this.mvc.perform(post("/api/organization/list").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("2"))
                .andExpect(jsonPath("$.[0].name").value("World of Wonder"))
                .andExpect(jsonPath("$.[0].isActive").value("true"));
    }

    @Test
    @Transactional
    public void fullListTest() throws Exception {
        LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("name","net");
        params.add("inn","24135846856461635384");
        params.add("isActive","true");
        this.mvc.perform(post("/api/organization/list").params(params))
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
        LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("name","Test Org");
        params.add("fullName","Testers Org.");
        params.add("inn","696969");
        params.add("kpp","133769");
        params.add("address","Test Town");
        this.mvc.perform(post("/api/organization/save").params(params))
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
        LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("name","The Test Org");
        params.add("fullName","The Testers Org.");
        params.add("inn","69696969");
        params.add("kpp","13376969");
        params.add("address","The Test Town");
        params.add("phone","+7-965-632-47-89");
        params.add("isActive","true");
        this.mvc.perform(post("/api/organization/save").params(params))
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
        LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("id","2");
        params.add("name","Test Org");
        params.add("fullName","Testers Org.");
        params.add("inn","696969");
        params.add("kpp","133769");
        params.add("address","Test Town");
        params.add("isActive","false");
        this.mvc.perform(post("/api/organization/update").params(params))
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
        LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("id","1");
        params.add("name","The Test Org");
        params.add("fullName","The Testers Org.");
        params.add("inn","69696969");
        params.add("kpp","13376969");
        params.add("address","The Test Town");
        params.add("phone","+7-965-632-47-89");
        params.add("isActive","true");
        this.mvc.perform(post("/api/organization/update").params(params))
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
