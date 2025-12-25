package rnd.dev.expensemanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rnd.dev.expensemanagement.dto.response.UserInfoResponse;

import static rnd.dev.expensemanagement.constant.ApiUrlConstants.AUTH_VALIDATE_SERVICE_URL;

@Slf4j
@Service
public class AuthClientService {

    private final RestTemplate restTemplate;

    public AuthClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserInfoResponse validateToken(String token) {
        log.info("AuthClientService :: validateToken :: token : {}", token);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<UserInfoResponse> response =
                restTemplate.exchange(AUTH_VALIDATE_SERVICE_URL, HttpMethod.GET, request, UserInfoResponse.class);

        return response.getBody();
    }
}
