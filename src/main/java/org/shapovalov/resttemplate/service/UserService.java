package org.shapovalov.resttemplate.service;

import org.shapovalov.resttemplate.model.User;

public interface UserService {

    void getAllUsers();

    String saveUser(User user);

    String updateUser(User user);

    String deleteUser(Long id);
}
