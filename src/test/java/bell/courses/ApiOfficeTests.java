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
public class ApiOfficeTests {

    @Autowired
    MockMvc mvc;


    @Test
    @Transactional
    public void listTest() throws Exception {
        this.mvc.perform(post("/api/office/list").param("orgId", "2"))
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
        LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("orgId","2");
        params.add("name","Testers Office");
        params.add("address","Test st.");
        this.mvc.perform(post("/api/office/save").params(params))
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
        LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("orgId","2");
        params.add("name","The Testers Office");
        params.add("address","Test st., Test Town");
        params.add("phone","+7-962-56-62-632");
        params.add("isActive","true");
        this.mvc.perform(post("/api/office/save").params(params))
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
        LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("id","2");
        params.add("name","Testers Office");
        params.add("address","Test st.");
        this.mvc.perform(post("/api/office/update").params(params))
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
        LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("id","3");
        params.add("name","The Testers Office");
        params.add("address","Test st., Test Town");
        params.add("phone","+7-962-56-62-632");
        params.add("isActive","false");
        this.mvc.perform(post("/api/office/update").params(params))
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
