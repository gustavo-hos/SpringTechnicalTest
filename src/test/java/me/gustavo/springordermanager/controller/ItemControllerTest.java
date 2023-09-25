package me.gustavo.springordermanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.gustavo.springordermanager.model.Item;
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
public class ItemControllerTest {

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
    @DisplayName("Should return the list of items")
    @Order(1)
    public void testGetItems() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/items"));

        result.andExpect(status().isOk());

        String providedResult = result.andReturn().getResponse().getContentAsString();

        String expectedResult = "[" +
                "{\"id\":1,\"name\":\"Product A\",\"sku\":\"PA001\"}," +
                "{\"id\":2,\"name\":\"Product B\",\"sku\":\"PB002\"}," +
                "{\"id\":3,\"name\":\"Product C\",\"sku\":\"PC003\"}," +
                "{\"id\":4,\"name\":\"Product D\",\"sku\":\"PD004\"}," +
                "{\"id\":5,\"name\":\"Product E\",\"sku\":\"PE005\"}," +
                "{\"id\":6,\"name\":\"Product F\",\"sku\":\"PF006\"}," +
                "{\"id\":7,\"name\":\"Product G\",\"sku\":\"PG007\"}," +
                "{\"id\":8,\"name\":\"Product H\",\"sku\":\"PH008\"}," +
                "{\"id\":9,\"name\":\"Product I\",\"sku\":\"PI009\"}," +
                "{\"id\":10,\"name\":\"Product J\",\"sku\":\"PJ010\"}" +
                "]";

        assertEquals("Values don't match: ", expectedResult, providedResult);
    }

    @Test
    @DisplayName("Should create a new item and return the result")
    @Order(2)
    public void testPostItem() throws Exception {
        Item item = new Item();

        item.setName("Product K");
        item.setSku("PK011");

        ResultActions result = mockMvc.perform(
                post("/api/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item))
        );

        result.andExpect(status().isOk());

        String providedResult = result.andReturn().getResponse().getContentAsString();

        String expectedResult = "{\"id\":11,\"name\":\"Product K\",\"sku\":\"PK011\"}";

        assertEquals("Values don't match: ", expectedResult, providedResult);
    }

    @Test
    @DisplayName("Should display last inserted item")
    @Order(3)
    public void testGetItemId() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/item/" + 11)
        );

        result.andExpect(status().isOk());

        String providedResult = result.andReturn().getResponse().getContentAsString();

        String expectedResult = "{\"id\":11,\"name\":\"Product K\",\"sku\":\"PK011\"}";

        assertEquals("Values don't match: ", expectedResult, providedResult);
    }

    @Test
    @DisplayName("Should change name and SKU of last inserted item")
    @Order(4)
    public void testPutItem() throws Exception {
        Item item = new Item();

        item.setId(11);
        item.setName("Product L");
        item.setSku("PL012");

        ResultActions result = mockMvc.perform(
                put("/api/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item))
        );

        result.andExpect(status().isOk());

        String providedResult = result.andReturn().getResponse().getContentAsString();

        String expectedResult = "{\"id\":11,\"name\":\"Product L\",\"sku\":\"PL012\"}";

        assertEquals("Values don't match: ", expectedResult, providedResult);
    }

    @Test
    @DisplayName("Should delete last inserted item")
    @Order(5)
    public void testDeleteItem() throws Exception {
        ResultActions result = mockMvc.perform(
                delete("/api/item/" + 11)
        );

        result.andExpect(status().is2xxSuccessful());
    }
}
