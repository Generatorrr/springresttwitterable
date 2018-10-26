package com.example.springresttwitterable.service;

import com.example.springresttwitterable.entity.Role;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.UserDTO;
import com.example.springresttwitterable.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${hostname}")
    private String hostname;

    // In the moment @Autowired is not so cool to use for inject dependencies in needed class. Use constructor's field:)
    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(String username, Map<String, String> form, User user)
    {

        Set<String> roles = Arrays
                .stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        user.setName(username);
        userRepository.save(user);
        
    }

    public void updateProfile(User user, UserDTO userToUpdate)
    {
        user.setEmail(userToUpdate.getEmail());
        user.setName(userToUpdate.getName());
        userRepository.save(user);
    }

    public void subscribe(User currentUser, User user) {

        user.getSubscribers().add(currentUser);
        userRepository.save(user);
    }

    public void unsubscribe(User currentUser, User user) {

        user.getSubscribers().remove(currentUser);
        userRepository.save(user);
    }
}
