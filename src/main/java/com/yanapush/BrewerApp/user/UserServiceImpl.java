package com.yanapush.BrewerApp.user;

import com.yanapush.BrewerApp.recipe.Recipe;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @NonNull
    UserRepository repository;

    @Override
    public User getUser(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User getUser(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    @Override
    public User getUserByRecipe(Recipe recipe) {
        return repository.findByRecipesContains(recipe).orElse(null);
    }

    @Override
    public ResponseEntity<?> addUser(User user) {
        try {
            repository.save(user);
        } catch (HibernateException e) {
            return new ResponseEntity<>(MessageConstants.ERROR_ADDING_USER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(MessageConstants.SUCCESS_ADDING_USER, HttpStatus.OK);
    }

    @Override
    public void deleteUser(User user) {
        repository.delete(user);
    }

    @Override
    public boolean deleteUser(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeUserPassword(String username, String password) {
        User user = repository.findByUsername(username).orElse(null);
        if (user == null) {
            return false;
        }
        user.setPassword(password);
        repository.save(user);
        return true;
    }
}
