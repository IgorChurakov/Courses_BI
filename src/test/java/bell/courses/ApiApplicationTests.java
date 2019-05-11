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
public class ApiApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    public void saveSimpleUserTest() throws Exception {
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
    public void saveFullUserTest() throws Exception {
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
    public void getUserTest() throws Exception {
        this.mvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.position").value("Director"));
    }

    @Test
    @Transactional
    public void listUserTest() throws Exception {
        this.mvc.perform(post("/api/user/list").param("officeId", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("4"))
                .andExpect(jsonPath("$.[0].position").value("Director"))
                .andExpect(jsonPath("$.[1].id").value("5"))
                .andExpect(jsonPath("$.[1].position").value("Advisor"));
    }

    @Test
    @Transactional
    public void updateSimpleUserTest() throws Exception {
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
    public void updateFullUserTest() throws Exception {
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

    @Test
    @Transactional
    public void listDocsTest() throws Exception {
        this.mvc.perform(get("/api/docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Паспорт гражданина Российской Федерации"))
                .andExpect(jsonPath("$.[0].code").value("21"))
                .andExpect(jsonPath("$.[4].name").value("Военный билет"))
                .andExpect(jsonPath("$.[4].code").value("7"));
    }

    @Test
    @Transactional
    public void listCountriesTest() throws Exception {
        this.mvc.perform(get("/api/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Russian Federation"))
                .andExpect(jsonPath("$.[0].code").value("643"))
                .andExpect(jsonPath("$.[6].name").value("Switzerland"))
                .andExpect(jsonPath("$.[6].code").value("756"));
    }

    @Test
    @Transactional
    public void listOfficeTest() throws Exception {
        this.mvc.perform(post("/api/office/list").param("orgId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("3"))
                .andExpect(jsonPath("$.[0].name").value("Office of Fantasies"))
                .andExpect(jsonPath("$.[0].isActive").value("true"));
    }

    @Test
    @Transactional
    public void getOfficeTest() throws Exception {
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
    public void saveSimpleOfficeTest() throws Exception {
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
    public void saveFullOfficeTest() throws Exception {
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
    public void updateSimpleOfficeTest() throws Exception {
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
    public void updateFullOfficeTest() throws Exception {
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

    @Test
    @Transactional
    public void listSimpleOrganizationTest() throws Exception {
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
    public void listFullOrganizationTest() throws Exception {
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
    public void getOrganizationTest() throws Exception {
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
    public void saveSimpleOrganizationTest() throws Exception {
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
    public void saveFullOrganizationTest() throws Exception {
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
    public void updateSimpleOrganizationTest() throws Exception {
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
    public void updateFullOrganizationTest() throws Exception {
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