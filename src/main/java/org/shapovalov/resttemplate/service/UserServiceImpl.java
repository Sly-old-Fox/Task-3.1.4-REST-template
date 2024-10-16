package org.shapovalov.resttemplate.service;

import org.shapovalov.resttemplate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private String sessionID = null;
    private final RestTemplate restTemplate;
    private static final String URL = "http://94.198.50.185:7081/api/users";

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getAllUsers() {
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            HttpHeaders headers = response.getHeaders();
            List<String> cookies = headers.get("Set-Cookie");
            if (cookies != null && !cookies.isEmpty()) {
                for (String cookie : cookies) {
                    if (cookie.startsWith("JSESSIONID=")) {
                        sessionID = cookie.split(";")[0];
                    }
                }
            }
        }
    }

    public String saveUser(User user){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", sessionID);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
        return response.getBody();
    }

    public String updateUser(User user){
    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", sessionID);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.PUT, request, String.class);
        return response.getBody();
    }

    public String deleteUser(Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", sessionID);
        HttpEntity<User> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL+"/"+id, HttpMethod.DELETE, request, String.class);
        return response.getBody();
    }
}
