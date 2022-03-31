package com.yanapush.BrewerApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.controller.ControllerAdvisor;
import com.yanapush.BrewerApp.entity.Coffee;
import com.yanapush.BrewerApp.controller.CoffeeController;
import com.yanapush.BrewerApp.exception.EntityNotFoundException;
import com.yanapush.BrewerApp.service.CoffeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {CoffeeController.class, CoffeeServiceImpl.class, ControllerAdvisor.class})
@WebMvcTest
public class CoffeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CoffeeServiceImpl coffeeServiceImpl;

    private final MessageConstants constants = new MessageConstants();

    Coffee coffee1 = new Coffee(1,"Indonesia Frinsa Manis", "Indonesia", "anaerobic");
    Coffee coffee2 = new Coffee(2,"Kenya Gichataini", "Kenya", "washed");
    Coffee coffee3 = new Coffee(3,"Colombia Finca Lourdes", "Colombia", "dry");

    @Test
    public void getAllCoffee_success() throws Exception {
        List<Coffee> records = new ArrayList<>(Arrays.asList(coffee1, coffee2, coffee3));

        Mockito.when(coffeeServiceImpl.getCoffee()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/coffee")
                        .with(csrf())
                        .with(user(String.valueOf(1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].coffee_name").value("Indonesia Frinsa Manis"));
    }

    @Test
    public void getCoffeeById_success() throws Exception {
        Mockito.when(coffeeServiceImpl.getCoffee(1)).thenReturn(coffee1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/coffee")
                        .with(csrf())
                        .with(user(String.valueOf(1)))
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.coffee_name").value("Indonesia Frinsa Manis"));
    }

    @Test
    public void getCoffeeById_NotFound() throws Exception {
        Mockito.when(coffeeServiceImpl.getCoffee(4)).thenThrow(new EntityNotFoundException(String.format(constants.ERROR_GETTING_BY_ID, "coffee", 4)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/coffee")
                        .with(csrf())
                        .with(user(String.valueOf(1)))
                        .param("id", "4")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(matchesPattern("^.*" + String.format(constants.ERROR_GETTING_BY_ID, "coffee", 4) + ".*$")));
    }

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        when(coffeeServiceImpl.addCoffee(coffee1)).thenReturn(coffee1);
        mockMvc.perform(post("/coffee")
                        .with(csrf())
                        .with(user(String.valueOf(1)))
                        .content(objectMapper.writeValueAsString(coffee1))
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenNullName_thenReturns400() throws Exception {
        Coffee coffee = new Coffee();
        coffee.setCountry("Indonesia");
        coffee.setProcess("anaerobic");
        when(coffeeServiceImpl.addCoffee(coffee)).thenReturn(null);
        mockMvc.perform(post("/coffee")
                        .with(csrf())
                        .with(user(String.valueOf(1)))
                        .content(objectMapper.writeValueAsString(coffee))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenValidInput_thenMapsToBusinessModel() throws Exception {
        Coffee coffee = new Coffee();
        coffee.setCoffee_name("Indonesia Frinsa Manis");
        coffee.setCountry("Indonesia");
        coffee.setProcess("anaerobic");
        when(coffeeServiceImpl.addCoffee(coffee)).thenReturn(coffee);
        mockMvc.perform(post("/coffee")
                        .with(csrf())
                        .with(user(String.valueOf(1)))
                        .content(objectMapper.writeValueAsString(coffee))
                        .contentType("application/json"))
                .andExpect(status().isOk());

        ArgumentCaptor<Coffee> coffeeCaptor = ArgumentCaptor.forClass(Coffee.class);
        verify(coffeeServiceImpl, times(1)).addCoffee(coffeeCaptor.capture());
        assertThat(coffeeCaptor.getValue().getCoffee_name()).isEqualTo("Indonesia Frinsa Manis");
        assertThat(coffeeCaptor.getValue().getCountry()).isEqualTo("Indonesia");
        assertThat(coffeeCaptor.getValue().getProcess()).isEqualTo("anaerobic");
        assertThat(coffeeCaptor.getValue().getDescription()).isEqualTo(null);
    }

    @Test
    public void deleteCoffee_success() throws Exception
    {
        Mockito.when(coffeeServiceImpl.deleteCoffee(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/coffee")
                        .with(csrf())
                        .with(user(String.valueOf(1)))
                        .param("id", "1")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.format(constants.SUCCESS_DELETING, "coffee")));

    }

    @Test
    public void deleteCoffee_NotFound() throws Exception
    {
        Mockito.when(coffeeServiceImpl.deleteCoffee(4)).thenThrow(new EntityNotFoundException(String.format(constants.ERROR_GETTING_BY_ID, "coffee", 4)));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/coffee")
                        .with(csrf())
                        .with(user(String.valueOf(1)))
                        .param("id", "4")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(matchesPattern("^.*" + String.format(constants.ERROR_GETTING_BY_ID, "coffee", 4)+ ".*$")));

    }
}
