package me.gustavo.springordermanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.gustavo.springordermanager.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

//    @Autowired
//    UserController userController;
//
//    @MockBean
//    UserService userServiceMock;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return the list of users")
    @Order(1)
    public void testGetUsers() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/users"));

        result.andExpect(status().isOk());

        String providedResult = result.andReturn().getResponse().getContentAsString();

        String expectedResult = "[" +
                "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}," +
                "{\"id\":2,\"name\":\"Jane Smith\",\"email\":\"jane.smith@example.com\"}," +
                "{\"id\":3,\"name\":\"Michael Johnson\",\"email\":\"michael.johnson@example.com\"}," +
                "{\"id\":4,\"name\":\"Emily Davis\",\"email\":\"emily.davis@example.com\"}," +
                "{\"id\":5,\"name\":\"William Brown\",\"email\":\"william.brown@example.com\"}," +
                "{\"id\":6,\"name\":\"Nico Hemmer\",\"email\":\"nico.hemmer@example.com\"}," +
                "{\"id\":7,\"name\":\"Stephan Rocker\",\"email\":\"stephan.rocker@example.com\"}," +
                "{\"id\":8,\"name\":\"Steve Bronks\",\"email\":\"steve.bronks@example.com\"}," +
                "{\"id\":9,\"name\":\"Dayna Marie\",\"email\":\"dayna.marie@example.com\"}," +
                "{\"id\":10,\"name\":\"Joana Hills\",\"email\":\"joana.hills@example.com\"}" +
                "]";

        assertEquals("Values don't match: ", expectedResult, providedResult);
    }

    @Test
    @DisplayName("Should create a new user and return the result")
    @Order(2)
    public void testPostUser() throws Exception {
        User user = new User();

        user.setName("Alfred Pennyworth");
        user.setEmail("alfred.pennyworth@example.com");

        ResultActions result = mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        );

        result.andExpect(status().isOk());

        String providedResult = result.andReturn().getResponse().getContentAsString();

        String expectedResult = "{\"id\":11,\"name\":\"Alfred Pennyworth\",\"email\":\"alfred.pennyworth@example.com\"}";

        assertEquals("Values don't match: ", expectedResult, providedResult);
    }

    @Test
    @DisplayName("Should display last inserted user")
    @Order(3)
    public void testGetUserId() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/user/" + 11)
        );

        result.andExpect(status().isOk());

        String providedResult = result.andReturn().getResponse().getContentAsString();

        String expectedResult = "{\"id\":11,\"name\":\"Alfred Pennyworth\",\"email\":\"alfred.pennyworth@example.com\"}";

        assertEquals("Values don't match: ", expectedResult, providedResult);
    }

    @Test
    @DisplayName("Should change name and email of last inserted user")
    @Order(4)
    public void testPutUser() throws Exception {
        User user = new User();

        user.setId(11);
        user.setName("Edward Nigma");
        user.setEmail("edward.nigma@example.com");

        ResultActions result = mockMvc.perform(
                put("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        );

        result.andExpect(status().isOk());

        String providedResult = result.andReturn().getResponse().getContentAsString();

        String expectedResult = "{\"id\":11,\"name\":\"Edward Nigma\",\"email\":\"edward.nigma@example.com\"}";

        assertEquals("Values don't match: ", expectedResult, providedResult);
    }

    @Test
    @DisplayName("Should delete last inserted user")
    @Order(5)
    public void testDeleteUser() throws Exception {
        ResultActions result = mockMvc.perform(
                delete("/api/user/" + 11)
        );

        result.andExpect(status().is2xxSuccessful());
    }
}
