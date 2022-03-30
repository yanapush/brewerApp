package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.dao.UserRepository;
import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @NonNull
    UserRepository repository;

    private MessageConstants constants = new MessageConstants();

    @Override
    public User getUser(int id) {
        log.info("getting user with id=" + id);
        return repository.findById(id).orElseThrow(() -> new BadCredentialsException(String.format(constants.ERROR_GETTING_BY_ID, "user", id)));
    }

    @Override
    public User getUser(String username) {
        log.info("getting user " + username);
        return repository.findByUsername(username).orElseThrow(() -> new BadCredentialsException(String.format(constants.ERROR_GETTING_BY_FIELD, "user", "username", username)));
    }

    @Override
    public User getUserByRecipe(Recipe recipe) {
        log.info("getting author of recipe=" + recipe.toString());
        return repository.findByRecipesContains(recipe).orElseThrow(() -> new BadCredentialsException(String.format(constants.ERROR_GETTING, "user")));
    }

    @Override
    public boolean addUser(User user) {
        log.info("adding user " + user.toString());
        return repository.save(user) == user;
    }

    @Override
    public boolean deleteUser(User user) {
        log.info("getting user " + user.getUsername());
        if (repository.existsById(user.getId())) {
            log.info("deleting user " + user.getUsername());
            repository.delete(user);
            return !repository.existsById(user.getId());
        }
        log.error("user " + user.getUsername() + " doesn't exist");
        throw new BadCredentialsException(String.format(constants.ERROR_GETTING_BY_ID, "user", user.getId()));
    }

    @Override
    public boolean deleteUser(int id) {
        log.info("looking for user with id=" + id);
        if (repository.existsById(id)) {
            log.info("deleting user with id=" + id);
            repository.deleteById(id);
            return !repository.existsById(id);
        }
        log.error("user with id=" + id + " doesn't exist");
        throw new BadCredentialsException(String.format(constants.ERROR_GETTING_BY_ID, "user", id));
    }

    @Override
    public boolean changeUserPassword(String username, String password) {
        log.error("looking for user " + username);
        User user = repository.findByUsername(username).orElseThrow(() -> new BadCredentialsException(String.format(constants.ERROR_GETTING_BY_FIELD, "user", "username", username)));
        log.info("setting " + username + " password to " + password);
        user.setPassword(password);
        log.info("saving " + username);
        return repository.save(user) == user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("getting user " + username);
        User user = repository.findByUsername(username).orElse(null);
        if (null == user) {
            throw new BadCredentialsException("User Not Found with username " + username);
        }
        return user;
    }
}
