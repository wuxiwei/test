package com.service;

public interface UserService {

    int createUser(UserDTO userDTO);

    String getUser(String name);
}
