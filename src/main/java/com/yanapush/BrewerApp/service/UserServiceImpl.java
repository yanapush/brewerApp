package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.dao.UserRepository;
import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;

    @Override
    public User getUser(String username) {
        return repository.findById(username).orElse(null);
    }

    @Override
    public User getUserByRecipe(Recipe recipe) {
        return repository.findByRecipesContains(recipe);
    }

    @Override
    public void addUser(User user) {
       repository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        repository.delete(user);
    }

    @Override
    public boolean deleteUser(String username) {
        if (repository.existsById(username)) {
            repository.deleteById(username);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeUserPassword(String username, String password) {
        User user = repository.findById(username).orElse(null);
        if (user == null) {
            return false;
        }
        user.setPassword(password);
        repository.save(user);
        return true;
    }
}
