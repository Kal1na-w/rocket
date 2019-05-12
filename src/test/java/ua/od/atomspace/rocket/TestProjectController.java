package ua.od.atomspace.rocket;


import com.ctc.wstx.sw.EncodingXmlWriter;
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
@Sql(scripts = {"/create-project-before.sql"})
public class TestProjectController {

    @Autowired
    private MockMvc mockMvc;

    // @Test
    // public void testGetAll() throws Exception {
    //     mockMvc.perform(get("/api/projects"))
    //         .andDo(print())
    //         .andExpect(status().isOk());
    // }

    // @Test
    // public void testGet() throws Exception {
    //     mockMvc.perform(get("/api/projects/1"))
    //             .andDo(print())
    //             .andExpect(status().isOk());
    // }

    // @Test
    // public void testPost() throws Exception {
    //     mockMvc.perform(post("/api/projects").content("{\"name\":\"TestProject\"}").contentType("application/json"))
    //             .andDo(print())
    //             .andExpect(status().isCreated());
    // }

    // @Test
    // public void testPut() throws Exception {
    //     mockMvc.perform(put("/api/projects/1").content("{\"name\":\"TestProjectIsChanged\"}").contentType("application/json"))
    //             .andDo(print())
    //             .andExpect(status().isCreated());


    // }

    // @Test
    // public void testDelete() throws Exception {
    //     mockMvc.perform(delete("/api/projects/1"))
    //             .andDo(print())
    //             .andExpect(status().isOk());
    // }
}
