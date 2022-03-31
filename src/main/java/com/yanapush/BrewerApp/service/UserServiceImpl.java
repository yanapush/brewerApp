package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.dao.UserRepository;
import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.Role;
import com.yanapush.BrewerApp.entity.User;
import com.yanapush.BrewerApp.exception.EntityDeletingFailedException;
import com.yanapush.BrewerApp.exception.EntityNotFoundException;
import com.yanapush.BrewerApp.exception.EntityNotSavedException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @NonNull
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private MessageConstants constants = new MessageConstants();

    @Override
    public User getUser(int id) {
        log.info("getting user with id=" + id);
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(constants.ERROR_GETTING_BY_ID, "user", id)));
    }

    @Override
    public User getUser(String username) {
        log.info("getting user " + username);
        return repository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(String.format(constants.ERROR_GETTING_BY_FIELD, "user", "username", username)));
    }

    @Override
    public User getUserByRecipe(Recipe recipe) {
        log.info("getting author of recipe=" + recipe.toString());
        return repository.findByRecipesContains(recipe).orElseThrow(() -> new EntityNotFoundException(String.format(constants.ERROR_GETTING, "user")));
    }

    @Override
    public boolean addUser(User user) {
        String encodedPassword
                = passwordEncoder.encode(user.getPassword());
        user.setEnabled(Boolean.TRUE);
        user.setPassword(encodedPassword);
        user.setUsername(user.getUsername());
        Role role = new Role();
        role.setAuthority("ROLE_USER");
        role.setUser(user);
        user.addRole(role);
        log.info("adding user " + user);
        if (repository.save(user) == user) {
            return true;
        }
        throw new EntityNotSavedException(String.format(constants.ERROR_ADDING, "user"));
    }

    @Override
    public boolean deleteUser(User user) {
        log.info("getting user " + user.getUsername());
        if (repository.existsById(user.getId())) {
            log.info("deleting user " + user.getUsername());
            repository.delete(user);
            if (repository.existsById(user.getId())) {
                throw new EntityDeletingFailedException(String.format(constants.ERROR_DELETING, "user"));
            }
            return true;
        }
        log.error("user " + user.getUsername() + " doesn't exist");
        throw new EntityNotFoundException(String.format(constants.ERROR_GETTING_BY_ID, "user", user.getId()));
    }

    @Override
    public boolean deleteUser(int id) {
        log.info("looking for user with id=" + id);
        if (repository.existsById(id)) {
            log.info("deleting user with id=" + id);
            repository.deleteById(id);
            if (repository.existsById(id)) {
                throw new EntityDeletingFailedException(String.format(constants.ERROR_DELETING_BY_ID, "user", id));
            }
            return true;
        }
        log.error("user with id=" + id + " doesn't exist");
        throw new EntityNotFoundException(String.format(constants.ERROR_GETTING_BY_ID, "user", id));
    }

    @Override
    public boolean changeUserPassword(String username, String password) {
        log.error("looking for user " + username);
        password = passwordEncoder.encode(password);
        User user = repository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(String.format(constants.ERROR_GETTING_BY_FIELD, "user", "username", username)));
        log.info("setting " + username + " password to " + password);
        user.setPassword(password);
        log.info("saving " + username);
        if (repository.save(user) != user) {
            throw new EntityNotSavedException(String.format(constants.ERROR_ADDING, "password"));
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("getting user " + username);
        User user = repository.findByUsername(username).orElse(null);
        if (null == user) {
            throw new EntityNotFoundException(String.format(constants.ERROR_GETTING_BY_FIELD, "user", "username", username));
        }
        return user;
    }
}
