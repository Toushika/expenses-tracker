package rnd.dev.authmanagement.service;

import rnd.dev.authmanagement.dto.response.UserInfoResponse;

public interface ValidateUserService {

    UserInfoResponse validate(String authHeader);
}
