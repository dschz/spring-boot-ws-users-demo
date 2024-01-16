package com.dsc.frm.springboot.mapper;

import com.dsc.frm.springboot.dto.UserDto;
import com.dsc.frm.springboot.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author DSchneider
 */
public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public static User mapToUser(UserDto dto) {
        return new User(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getEmail());
    }
}
