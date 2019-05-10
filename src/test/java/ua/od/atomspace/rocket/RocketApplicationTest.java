package ua.od.atomspace.rocket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RocketApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testContext() throws Exception {
        mockMvc.perform(post("/api/auth/signup").content("{" +
                "\"username\":\"Testuser\"," +
                "\"firstName\":\"Testname\"," +
                "\"lastName\":\"Testname\"," +
                "\"password\":\"password\"," +
                "\"email\":\"kalina.vald@gmail.com\"" +
                "}").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/auth/signin").content("{" +
                "\"username\":\"Testuser\"," +
                "\"password\":\"password\"" +
                "}").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}