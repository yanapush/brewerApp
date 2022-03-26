package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.dao.UserRepository;
import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    @NonNull
    UserRepository repository;

    @Override
    public User getUser(int id) {
        return repository.findById(id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
    }

    @Override
    public User getUser(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
    }

    @Override
    public User getUserByRecipe(Recipe recipe) {
        return repository.findByRecipesContains(recipe).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
    }

    @Override
    public boolean addUser(User user) {
            return repository.save(user) == user;
    }

    @Override
    public boolean deleteUser(User user) {
        if (repository.existsById(user.getId())) {
            repository.delete(user);
            return !repository.existsById(user.getId());
        }
        throw new BadCredentialsException(MessageConstants.ERROR_GETTING);
    }

    @Override
    public boolean deleteUser(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return !repository.existsById(id);
        }
        throw new BadCredentialsException(MessageConstants.ERROR_GETTING);
    }

    @Override
    public boolean changeUserPassword(String username, String password) {
        User user = repository.findByUsername(username).orElseThrow(() ->new BadCredentialsException(MessageConstants.ERROR_GETTING));
        user.setPassword(password);
        return repository.save(user) == user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElse(null);

        if (null == user) {
            throw new UsernameNotFoundException("User Not Found with userName " + username);
        }
        return user;
    }
}
