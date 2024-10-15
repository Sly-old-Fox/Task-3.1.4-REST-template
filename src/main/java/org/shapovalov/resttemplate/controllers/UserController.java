package org.shapovalov.resttemplate.controllers;

import org.shapovalov.resttemplate.model.User;
import org.shapovalov.resttemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getCode() {
        userService.getAllUsers();
        User user = new User(3L, "James", "Brown", (byte) 28);
        String firstPart = userService.saveUser(user);
        user.setName("Thomas");
        user.setLastName("Shelby");
        String secondPart = userService.updateUser(user);
        String thirdPart = userService.deleteUser(user.getId());
        return firstPart + secondPart + thirdPart;
    }
}
