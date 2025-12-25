package rnd.dev.authmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rnd.dev.authmanagement.constant.DatePatternConstants;
import rnd.dev.authmanagement.dto.request.SignupRequest;
import rnd.dev.authmanagement.dto.response.SignupResponse;
import rnd.dev.authmanagement.entity.User;
import rnd.dev.authmanagement.entity.enums.Role;
import rnd.dev.authmanagement.error.exception.UserAlreadyExistException;
import rnd.dev.authmanagement.utility.DateConverter;
import rnd.dev.authmanagement.utility.PasswordUtility;
import rnd.dev.authmanagement.utility.Sha256Utility;

import java.util.Date;

import static rnd.dev.authmanagement.constant.ExceptionMessageConstants.USER_ALREADY_EXIST_MESSAGE;
import static rnd.dev.authmanagement.constant.ResponseMessage.SUCCESSFUL_USER_CREATION_RESPONSE;

@Slf4j
@Service
public class SignupServiceImpl implements SignupService {

    private final SignupAnemicService signupAnemicService;

    public SignupServiceImpl(SignupAnemicService signupAnemicService) {
        this.signupAnemicService = signupAnemicService;
    }

    @Override
    public SignupResponse doSignup(SignupRequest signupRequest) {
        if (signupAnemicService.isUserExist(signupRequest.getEmail())) {
            throw new UserAlreadyExistException(USER_ALREADY_EXIST_MESSAGE);
        }
        User user = buildUser(signupRequest);
        User savedUser = signupAnemicService.doSignup(user);
        return buildSignupResponse(savedUser);
    }


    private User buildUser(SignupRequest signupRequest) {

        log.info("SignupServiceImpl :: buildUserDto :: signupRequest : {}", signupRequest);

        return User.builder()
                .userId(Sha256Utility.hash(signupRequest.getEmail() + System.currentTimeMillis()))
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .passwordHash(PasswordUtility.encode(signupRequest.getPassword()))
                .role(Role.USER)
                .createdAt(DateConverter.getConvertedDate(DatePatternConstants.DATE_PATTERN, new Date()))
                .build();
    }

    private SignupResponse buildSignupResponse(User user) {
        return SignupResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .message(SUCCESSFUL_USER_CREATION_RESPONSE)
                .build();
    }

}
