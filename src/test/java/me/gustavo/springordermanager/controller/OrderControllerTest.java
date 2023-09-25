package me.gustavo.springordermanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.gustavo.springordermanager.model.dto.OrderDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return the list of orders")
    @org.junit.jupiter.api.Order(1)
    public void testGetOrders() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/orders"));

        result.andExpect(status().isOk());

        String providedResult = result.andReturn().getResponse().getContentAsString();

        assertFalse("Response body is empty", providedResult.isEmpty());

//        String expectedResult = "[" +
//                "{\"uuid\":\"81387eea-03c8-4f8e-861d-62feace28299\",\"item\":{\"id\":5,\"name\":\"Product E\",\"sku\":\"PE005\"},\"user\":{\"id\":1},\"quantity\":8,\"status\":\"PENDING\",\"creationDate\":null,\"stockMovements\":[]}," +
//                "{\"uuid\":\"b3fff179-15d5-4059-90ad-2dc1df86e073\",\"item\":{\"id\":3,\"name\":\"Product C\",\"sku\":\"PC003\"},\"user\":{\"id\":5},\"quantity\":3,\"status\":\"PENDING\",\"creationDate\":null,\"stockMovements\":[]}," +
//                "{\"uuid\":\"955d85aa-0e5f-40e5-ba8c-19f7ff2f1281\",\"item\":{\"id\":4,\"name\":\"Product D\",\"sku\":\"PD004\"},\"user\":{\"id\":8},\"quantity\":22,\"status\":\"COMPLETED\",\"creationDate\":null,\"stockMovements\":[]}," +
//                "{\"uuid\":\"5015e7e9-7802-47db-a943-0dffa2772f8e\",\"item\":{\"id\":8,\"name\":\"Product H\",\"sku\":\"PH008\"},\"user\":{\"id\":2},\"quantity\":65,\"status\":\"COMPLETED\",\"creationDate\":null,\"stockMovements\":[]}," +
//                "{\"uuid\":\"023d3edc-7606-4c16-b7e8-b4a3e47344f2\",\"item\":{\"id\":9,\"name\":\"Product I\",\"sku\":\"PI009\"},\"user\":{\"id\":6},\"quantity\":12,\"status\":\"PENDING\",\"creationDate\":null,\"stockMovements\":[]}," +
//                "{\"uuid\":\"02150b93-a6bf-44c1-96a4-d59a6505c9a3\",\"item\":{\"id\":4,\"name\":\"Product D\",\"sku\":\"PD004\"},\"user\":{\"id\":7},\"quantity\":13,\"status\":\"COMPLETED\",\"creationDate\":null,\"stockMovements\":[]}," +
//                "{\"uuid\":\"78d27e85-1c07-47fb-8cab-1fdd2502804d\",\"item\":{\"id\":2,\"name\":\"Product B\",\"sku\":\"PB002\"},\"user\":{\"id\":2},\"quantity\":42,\"status\":\"COMPLETED\",\"creationDate\":null,\"stockMovements\":[]}," +
//                "{\"uuid\":\"e28eec66-9d25-4e6d-8cc8-1fee651d20d3\",\"item\":{\"id\":6,\"name\":\"Product F\",\"sku\":\"PF006\"},\"user\":{\"id\":4},\"quantity\":8,\"status\":\"PENDING\",\"creationDate\":null,\"stockMovements\":[]}" +
//                "]";

//        assertEquals("Values don't match: ", providedResult, expectedResult);
    }

    @Test
    @DisplayName("Should create a new order and return the result")
    @org.junit.jupiter.api.Order(2)
    public void getPostOrder() throws Exception {
//        Item item = new Item();
//
//        item.setSku("PA001");
//
//        User user = new User();
//
//        user.setId(1);
//
//        Order order = new Order();
//
//        order.setUser(user);
//        order.setItem(item);
//        order.setQuantity(10);

        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(1);
        orderDto.setItemSku("PA001");
        orderDto.setQuantity(10);

        ResultActions result = mockMvc.perform(
                post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto))
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.uuid").isNotEmpty());
    }

    @Test
    @DisplayName("Should display the completion of provided orders")
    @org.junit.jupiter.api.Order(3)
    public void getGetOrderCompletion() throws Exception {
        String orderUuid = "5015e7e9-7802-47db-a943-0dffa2772f8e"; // should be completed;

        ResultActions result = mockMvc.perform(
                get("/api/order/" + orderUuid + "/completion")
        );

        result.andExpect(status().isOk());

        String providedResult = result.andReturn().getResponse().getContentAsString();
        String expectedResult = "Order is complete.";

        assertEquals("Values don't match: ", expectedResult, providedResult);


        String orderUuid2 = "023d3edc-7606-4c16-b7e8-b4a3e47344f2"; // should be pending;

        ResultActions result2 = mockMvc.perform(
                get("/api/order/" + orderUuid2 + "/completion")
        );

        result2.andExpect(status().isOk());

        String providedResult2 = result2.andReturn().getResponse().getContentAsString();
        String expectedResult2 = "Order is still being processed.";

        assertEquals("Values don't match: ", expectedResult2, providedResult2);
    }


    @Test
    @DisplayName("Should delete an order")
    @org.junit.jupiter.api.Order(5)
    public void testDeleteOrder() throws Exception {
        String orderToDelete = "22cd9e97-f3cb-4dde-b7b9-5c824c072166";

        ResultActions result = mockMvc.perform(
                delete("/api/order/" + orderToDelete)
        );

        result.andExpect(status().is2xxSuccessful());
    }
}
