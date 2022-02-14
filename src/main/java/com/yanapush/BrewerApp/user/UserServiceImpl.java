package com.yanapush.BrewerApp.user;

import com.yanapush.BrewerApp.recipe.Recipe;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @NonNull
    UserRepository repository;

    @Override
    public ResponseEntity<?> getUser(int id) {
        Optional<User> user = repository.findById(id);
        return (user.equals(Optional.empty()))
                ? new ResponseEntity<>(MessageConstants.ERROR_GETTING_USER, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUser(String username) {
        Optional<User> user = repository.findByUsername(username);
        return (user.equals(Optional.empty()))
                ? new ResponseEntity<>(MessageConstants.ERROR_GETTING_USER, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserByRecipe(Recipe recipe) {
        Optional<User> user = repository.findByRecipesContains(recipe);
        return (user.equals(Optional.empty()))
                ? new ResponseEntity<>(MessageConstants.ERROR_GETTING_USER, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(user, HttpStatus.OK);
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
    public ResponseEntity<?> changeUserPassword(String username, String password) {
        Optional<User> user = repository.findByUsername(username);
        if (user.equals(Optional.empty())) {
            return new ResponseEntity<>(MessageConstants.ERROR_CHANGING_PASSWORD, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.get().setPassword(password);
        repository.save(user.get());
        return new ResponseEntity<>(MessageConstants.SUCCESS_PASSWORD_CHANGE, HttpStatus.OK);
    }
}
