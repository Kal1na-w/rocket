package ua.od.atomspace.rocket;

import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.od.atomspace.rocket.domain.Course;

import javax.swing.plaf.PanelUI;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/create-courses-before.sql", "/create-lvl-before.sql","/create-content-before.sql"})
public class TestCourseController {

    @Autowired
    private MockMvc mockMvc;


    @Before
    @Transactional
    public void start() {

    }

    @Test
    public void testDeleteLevelByCourse() throws Exception {
        mockMvc.perform(delete("/api/courses/1/levels/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteContentByLvl() throws Exception {
        mockMvc.perform(delete("/api/courses/1/levels/1/contents/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetContentByLevel() throws Exception {
        mockMvc.perform(get("/api/courses/1/levels/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testPostContentByLvl() throws Exception {
        mockMvc.perform(post("/api/courses/1/levels/1/contents").contentType("application/json").content("{\"context\":\"SOme content\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllLevelsByCourse() throws Exception{
        mockMvc.perform(get("/api/courses/1/levels"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetLevelByCourse() throws Exception{
        mockMvc.perform(get("/api/courses/1/levels/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testPostLevelToCourse() throws Exception {
        mockMvc.perform(post("/api/courses/1/levels").contentType("application/json").content("{\"name\":\"TestLevel\"}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetOneCourse() throws Exception {
        mockMvc.perform(get("/api/courses/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testPutCourse() throws Exception {
        mockMvc.perform(put("/api/courses/1").contentType("application/json").content("{\"name\":\"OhHiMark\"}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/api/courses/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testPostCourses() throws Exception {
        Course course = new Course("testName",Byte.parseByte("2"));
        mockMvc.perform(post("/api/courses").content("{\"name\":\"" + course.getName() +"\",\"priority\":\"" + course.getPriority() + "\"}").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetCourses() throws Exception {
        mockMvc.perform(get("/api/courses"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
