package rnd.dev.authmanagement.service;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rnd.dev.authmanagement.dto.response.UserInfoResponse;
import rnd.dev.authmanagement.error.exception.InvalidAuthorizationHeaderException;
import rnd.dev.authmanagement.utility.JwtUtility;

import static rnd.dev.authmanagement.constant.ExceptionMessageConstants.AUTHORIZATION_HEADER_MESSAGE;
import static rnd.dev.authmanagement.constant.SecurityConstants.*;

@Slf4j
@Service
public class ValidateUserServiceImpl implements ValidateUserService {

    private final JwtUtility jwtUtility;

    public ValidateUserServiceImpl(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    public UserInfoResponse validate(String authHeader) {
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            throw new InvalidAuthorizationHeaderException(AUTHORIZATION_HEADER_MESSAGE);
        }
        String token = authHeader.substring(BEARER_REMOVAL_NUMBER); // remove "Bearer "

        Claims claims = jwtUtility.validateAndExtract(token);

        String userId = claims.getSubject();
        String role = claims.get(CLAIM_NAME, String.class);

        return builderUserInfoResponse(userId, role);
    }

    private UserInfoResponse builderUserInfoResponse(String userId, String role) {
        return UserInfoResponse.builder()
                .userId(userId)
                .role(role)
                .build();
    }

}
