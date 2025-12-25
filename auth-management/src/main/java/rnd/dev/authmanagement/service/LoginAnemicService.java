package rnd.dev.authmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rnd.dev.authmanagement.entity.User;
import rnd.dev.authmanagement.repository.UserRepository;

@Slf4j
@Service
public class LoginAnemicService {

    private final UserRepository userRepository;

    public LoginAnemicService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String email) {
        return userRepository.getUserByEmail(email).orElseThrow();
    }

}
