package com.snake.ladder.capillary.api;

import com.snake.ladder.capillary.service.impl.LudoServiceImpl;
import com.snake.ladder.capillary.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {LudoController.class, LudoServiceImpl.class})
@WebMvcTest
public class LudoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_ShouldReturn200_WhenGetMethodCalledToStartTheGame() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/start"))
                .andExpect(status().isOk()).andReturn();
        assertEquals(Constants.READY, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void test_ShouldReturn200_When_STOP_TheService() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/stop"))
                .andExpect(status().isOk()).andReturn();
        assertEquals(Constants.STOPPED, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void test_ShouldReturn200_When_POST_MethodCalledToStartTheGame_WithBody() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/start")
                        .content("{ \"START\": \"START\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();
        assertEquals(Constants.READY, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void test_ShouldReturn200_When_POST_WithoutBody() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/start").content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(Constants.NOT_READY, mvcResult.getResponse().getContentAsString());
    }


    @Test
    void test_ShouldReturn200_WhenGetMethodForNextMove() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/dice/2"))
                .andExpect(status().isOk()).andReturn();
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void test_ShouldReturn_ErrorMessage_WhenGetMethodCalled_WithInvalidNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/start"))
                .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/dice/78"))
                .andExpect(status().isOk()).andReturn();
        assertNotNull(mvcResult.getResponse().getContentAsString());
        assertEquals(Constants.NAN, mvcResult.getResponse().getContentAsString());
    }
}
