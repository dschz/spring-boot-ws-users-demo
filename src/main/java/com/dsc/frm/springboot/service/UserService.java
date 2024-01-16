package com.dsc.frm.springboot.service;

import com.dsc.frm.springboot.dto.UserDto;

import java.util.List;

/**
 * @author DSchneider
 */
public interface UserService {
    UserDto createUser(UserDto user);
    UserDto getUserById(Long userId);
    List<UserDto> getAllUsers();
    UserDto updateUser(UserDto user);
    void deleteUser(Long userId);
}
