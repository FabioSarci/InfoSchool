package com.infoschool.infoschool.mapper;

import org.springframework.stereotype.Component;

import com.infoschool.infoschool.dto.request.UserDto;
import com.infoschool.infoschool.model.User;

@Component
public class UserMapper {

    public UserDto userToDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setRoleId(user.getRole() != null ? user.getRole().getId() : 0);

        return userDto;
    }

    public User dtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setBirthDate(userDto.getBirthDate());

        return user;
    }
}