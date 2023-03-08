package com.example.bookexchangerauthorization.web.service;

import com.example.bookexchangerauthorization.domain.model.Role;
import com.example.bookexchangerauthorization.domain.model.dto.UserDto;
import com.example.bookexchangerauthorization.domain.model.entity.User;
import com.example.bookexchangerauthorization.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUserAccount(UserDto userDto) throws EmailExistsException {
        if (emailExist(userDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address: "
                            + userDto.getEmail());
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
}
