package com.benczykuadama.testdrive.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void a_returnsJsonWithIdOne_whenUrlIsHitForFirstTime() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/time").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void b_returnsJsonWithDefaultName_whenNameValueNotProvided() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/time").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", equalTo("You are still alive, my dear filthy worm!")));
    }

    @Test
    public void c_returnsJsonWithDateThatIsCloseToNow() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/time").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.now", lessThanOrEqualTo(LocalDateTime.now().toString())));

    }

    @Test()
    public void d_returnsJsonWithName_whenNameValueIsPassed() throws Exception {

        String name = "Alfonzo";

        mvc.perform(MockMvcRequestBuilders.get("/time")
                .accept(MediaType.APPLICATION_JSON)
                .param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", equalTo("You are still alive, my dear Alfonzo!")));
    }

    @Test
    public void e_returnsJsonWithIdFive_whenUrlIsHitForFifthTime() throws Exception {

        String name = "Alfonzo";

        mvc.perform(MockMvcRequestBuilders.get("/time").accept(MediaType.APPLICATION_JSON).param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(5)));
    }

}