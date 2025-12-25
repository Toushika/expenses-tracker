package rnd.dev.authmanagement.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rnd.dev.authmanagement.dto.request.SignupRequest;
import rnd.dev.authmanagement.dto.response.SignupResponse;
import rnd.dev.authmanagement.service.SignupService;

import static rnd.dev.authmanagement.constant.ApiUrlConstants.SIGNUP_URL;

@RestController
public class SignupController extends AbstractController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping(SIGNUP_URL)
    public SignupResponse doSignUp(@RequestBody SignupRequest signupRequest) {
        return signupService.doSignup(signupRequest);
    }
}
