package rnd.dev.authmanagement.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rnd.dev.authmanagement.dto.request.LoginRequest;
import rnd.dev.authmanagement.dto.response.LoginResponse;
import rnd.dev.authmanagement.service.LoginService;

import static rnd.dev.authmanagement.constant.ApiUrlConstants.LOGIN_URL;

@RestController
public class LoginController extends AbstractController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(LOGIN_URL)
    public LoginResponse doLogin(@RequestBody LoginRequest loginRequest) {
        return loginService.doLogin(loginRequest);
    }
}
