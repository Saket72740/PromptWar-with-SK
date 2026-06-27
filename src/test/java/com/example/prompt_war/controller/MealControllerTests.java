package com.example.prompt_war.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MealControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetHomeUnauthenticatedRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testGetHomeAuthenticatedSucceeds() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "admin");

        mockMvc.perform(get("/").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testGenerateMealPlanAuthenticated() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "admin");

        mockMvc.perform(post("/generate")
                        .session(session)
                        .param("dayType", "busy")
                        .param("dietPreference", "none")
                        .param("timeConstraint", "30")
                        .param("targetBudget", "25.0")
                        .param("fridgeIngredients", "eggs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.dayType").value("busy"))
                .andExpect(jsonPath("$.targetBudget").value(25.0));
    }
}
