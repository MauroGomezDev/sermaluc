package com.sermaluc.desafio.service;

import com.sermaluc.desafio.jwt.UserRequest;
import com.sermaluc.desafio.model.User;

import java.util.List;

public interface UserService {

    User createUser(UserRequest userRequest);
    User getUserByEmail(UserRequest userRequest);
    User updateUser(UserRequest userRequest);
    User patchUpdateUser(UserRequest userRequest);
    boolean deleteUserByEmail(UserRequest userRequest);
    String createJwtToken(User user);
    List<User> listar();


}
