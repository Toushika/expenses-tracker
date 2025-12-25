package rnd.dev.authmanagement.service;

import rnd.dev.authmanagement.dto.request.SignupRequest;
import rnd.dev.authmanagement.dto.response.SignupResponse;

public interface SignupService {

    SignupResponse doSignup(SignupRequest signupRequest);
}
