package rnd.dev.authmanagement.service;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rnd.dev.authmanagement.dto.response.UserInfoResponse;
import rnd.dev.authmanagement.utility.JwtUtility;

@Slf4j
@Service
public class ValidateUserServiceImpl implements ValidateUserService {

    private  final JwtUtility jwtUtility;

    public ValidateUserServiceImpl(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    public UserInfoResponse validate(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7); // remove "Bearer "

        Claims claims = jwtUtility.validateAndExtract(token);

        String userId = claims.getSubject();
        String role = claims.get("role", String.class);

        return builderUserInfoResponse(userId, role);
    }

    private UserInfoResponse builderUserInfoResponse(String userId, String role) {
        return UserInfoResponse.builder()
                .userId(userId)
                .role(role)
                .build();
    }

}
