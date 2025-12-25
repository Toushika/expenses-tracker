package rnd.dev.authmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rnd.dev.authmanagement.dto.request.LoginRequest;
import rnd.dev.authmanagement.dto.response.LoginResponse;
import rnd.dev.authmanagement.entity.User;
import rnd.dev.authmanagement.utility.JwtUtility;
import rnd.dev.authmanagement.utility.PasswordUtility;

import static rnd.dev.authmanagement.constant.ResponseMessage.SUCCESSFUL_LOGIN_MESSAGE;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    private final LoginAnemicService loginAnemicService;

    private final JwtUtility jwtUtility;

    public LoginServiceImpl(LoginAnemicService loginAnemicService, JwtUtility jwtUtility) {
        this.loginAnemicService = loginAnemicService;
        this.jwtUtility = jwtUtility;
    }

    @Override
    public LoginResponse doLogin(LoginRequest loginRequest) {
        log.info("doLogin :: loginRequest : {}", loginRequest);
        User user = loginAnemicService.getUser(loginRequest.getEmail());
        if (user == null) {
            throw new RuntimeException("No user available");
        }

        if (!PasswordUtility.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("You Entered wrong Password");
        }

        String token = jwtUtility.generateToken(user.getUserId(), user.getRole().name());

        return buildLoginResponse(token);

    }

    private static LoginResponse buildLoginResponse(String token) {
        return LoginResponse.builder()
                .token(token)
                .message(SUCCESSFUL_LOGIN_MESSAGE)
                .build();
    }

}
