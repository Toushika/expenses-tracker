package rnd.dev.authmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import rnd.dev.authmanagement.dto.response.UserInfoResponse;
import rnd.dev.authmanagement.service.ValidateUserService;

import static rnd.dev.authmanagement.constant.ApiUrlConstants.VALIDATE_URL;

@RestController
public class ValidateUserController extends AbstractController {

    private final ValidateUserService validateUserService;

    public ValidateUserController(ValidateUserService validateUserService) {
        this.validateUserService = validateUserService;
    }

    @GetMapping(VALIDATE_URL)
    public UserInfoResponse validateToken(@RequestHeader("Authorization") String authHeader) {
        return validateUserService.validate(authHeader);

    }
}
