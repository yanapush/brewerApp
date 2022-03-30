package com.yanapush.BrewerApp;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.dao.CoffeeRepository;
import com.yanapush.BrewerApp.entity.Coffee;
import com.yanapush.BrewerApp.service.CoffeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoffeeServiceTest {

    @Mock
    CoffeeRepository dao;

    @InjectMocks
    CoffeeServiceImpl coffeeServiceImpl;

    MessageConstants constants = new MessageConstants();

    Coffee coffee1 = new Coffee(1,"Indonesia Frinsa Manis", "Indonesia", "anaerobic");
    Coffee coffee2 = new Coffee(2,"Kenya Gichataini", "Kenya", "washed");
    Coffee coffee3 = new Coffee(3,"Colombia Finca Lourdes", "Colombia", "dry");

    List<Coffee> all_coffee = List.of(coffee1, coffee2, coffee3);
    @Test
    public void getAllCoffee_success() {
        when(dao.findAll()).thenReturn(all_coffee);

        List<Coffee> coffee = coffeeServiceImpl.getCoffee();

        assertEquals(3, coffee.size());
        assertEquals(all_coffee, coffee);
    }

    @Test
    public void getCoffeeById_success() {
        when(dao.findById(1)).thenReturn(Optional.ofNullable(coffee1));

        Coffee coffee = coffeeServiceImpl.getCoffee(1);

        assertEquals(1, coffee.getId());
        assertEquals("Indonesia Frinsa Manis", coffee.getCoffee_name());
        assertEquals("Indonesia", coffee.getCountry());
        assertEquals("anaerobic", coffee.getProcess());
    }

    @Test
    public void getCoffeeById_error() {
        when(dao.findById(5)).thenReturn(Optional.empty());
        Exception exception = assertThrows(BadCredentialsException.class, () -> coffeeServiceImpl.getCoffee(5));
        assertEquals(exception.getMessage(), String.format(constants.ERROR_GETTING_BY_ID, "coffee", 5));
    }

    @Test
    public void addCoffee_success() {
        when(dao.save(coffee2)).thenReturn(coffee2);
        assertTrue(coffeeServiceImpl.addCoffee(coffee2));
    }

    @Test
    public void addCoffee_error() {
        when(dao.save(coffee2)).thenReturn(null);
        assertFalse(coffeeServiceImpl.addCoffee(coffee2));
    }

    @Test
    public void deleteCoffee_success() {
        when(dao.existsById(2)).thenReturn(true).thenReturn(false);
        assertTrue(coffeeServiceImpl.deleteCoffee(2));
    }

    @Test
    public void deleteCoffee_error() {
        when(dao.existsById(2)).thenReturn(true).thenReturn(true);
        assertFalse(coffeeServiceImpl.deleteCoffee(2));
    }

    @Test
    public void deleteCoffee_notFound() {
        when(dao.existsById(2)).thenReturn(false).thenReturn(false);
        Exception exception = assertThrows(BadCredentialsException.class, () -> coffeeServiceImpl.deleteCoffee(2));
        assertEquals(exception.getMessage(), String.format(constants.ERROR_GETTING_BY_ID, "coffee", 2));
    }
}
