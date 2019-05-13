package bell.courses;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiCatalogueTests {

    @Autowired
    MockMvc mvc;


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
}
