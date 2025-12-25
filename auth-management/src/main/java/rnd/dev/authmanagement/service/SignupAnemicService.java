package rnd.dev.authmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rnd.dev.authmanagement.entity.User;
import rnd.dev.authmanagement.repository.UserRepository;

@Slf4j
@Service
public class SignupAnemicService {

    private final UserRepository userRepository;

    public SignupAnemicService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User doSignup(User user) {
        return userRepository.save(user);
    }

    public boolean isUserExist(String email) {
        return userRepository.getUserByEmail(email).isPresent();
    }

}
