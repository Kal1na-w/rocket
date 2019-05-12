package ua.od.atomspace.rocket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = { "/create-content-before.sql", "/create-lvl-before.sql", "/create-courses-before.sql" })
public class TestContentController {


    @Autowired
    private MockMvc mockMvc;

    // @Test
    // public void testGetAll() throws Exception {
    //     mockMvc.perform(get("/api/contents")).andDo(print()).andExpect(status().isOk());
    // }

    // @Test
    // public void testGet() throws Exception {
    //     mockMvc.perform(get("/api/contents/1")).andDo(print()).andExpect(status().isOk());
    // }

    // @Test
    // public void testPut() throws Exception {
    //     mockMvc.perform(put("/api/contents/1").content("{\"context\":\"Ok\"}").contentType("application/json"))
    //             .andDo(print()).andExpect(status().isCreated());
    // }
}
