package rnd.dev.authmanagement.service;

import rnd.dev.authmanagement.dto.request.LoginRequest;
import rnd.dev.authmanagement.dto.response.LoginResponse;

public interface LoginService {

    LoginResponse doLogin(LoginRequest loginRequest);
}