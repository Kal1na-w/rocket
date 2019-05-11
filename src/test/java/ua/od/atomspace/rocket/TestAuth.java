package ua.od.atomspace.rocket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestAuth {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAuthWithEmail() throws Exception {
        mockMvc.perform(post("/api/auth/signin").content("{" +
                "\"usernameOrEmail\":\"test@mail.com\"," +
                "\"password\":\"password\"" +
                "}").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthWithUsername() throws Exception {
        mockMvc.perform(post("/api/auth/signin").content("{" +
                "\"usernameOrEmail\":\"username\"," +
                "\"password\":\"password\"" +
                "}").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
