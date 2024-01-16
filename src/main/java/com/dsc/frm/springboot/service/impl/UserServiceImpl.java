package com.dsc.frm.springboot.service.impl;

import com.dsc.frm.springboot.dto.UserDto;
import com.dsc.frm.springboot.entity.User;
import com.dsc.frm.springboot.exception.EmailAlreadyExistsException;
import com.dsc.frm.springboot.exception.ResourceNotFoundException;
import com.dsc.frm.springboot.mapper.UserStructMapper;
import com.dsc.frm.springboot.repository.UserRepository;
import com.dsc.frm.springboot.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author DSchneider
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto user) {
        // ----- check email already exists, ex gestion des exceptions ---------

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if(optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists for the user");
        }

        //------ create ------------

        //mapper lib spring bean
        //User u = userRepository.save(modelMapper.map(user, User.class));
        //return modelMapper.map(u, UserDto.class);

        //mapper lib mapstruct
        User u = userRepository.save(UserStructMapper.MAPPER.map2User(user));
        return UserStructMapper.MAPPER.map2UserDto(u);

        //mapper specifique projet
        //User u = userRepository.save(UserMapper.mapToUser(user));
        //return UserMapper.mapToUserDto(u);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User u = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user", "id", userId)
        );
        return UserStructMapper.MAPPER.map2UserDto(u);
    }
    @Deprecated
    public UserDto getUserById_badPractice(Long userId) {
        //facon de faire bad practice, il faut utiliser "orElseThrow" ci-dessus
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            return UserStructMapper.MAPPER.map2UserDto(byId.get());//mapStruct lib
            //return modelMapper.map(byId.get(), UserDto.class);//modelMapper lib
            //return UserMapper.mapToUserDto(byId.get());//own mapper
        }
        throw new ResourceNotFoundException("user","id", userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> all = userRepository.findAll();
        return all.stream().map(user -> UserStructMapper.MAPPER.map2UserDto(user)).collect(Collectors.toList());
        //return all.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        //return all.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
        //return all.stream().map(user -> UserMapper.mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User dbUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("user", "id", user.getId())
        );
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setEmail(user.getEmail());
        User updUser = userRepository.save(dbUser);
        return UserStructMapper.MAPPER.map2UserDto(updUser);
        //return modelMapper.map(user, UserDto.class);
        //return UserMapper.mapToUserDto(updUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User u = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user", "id", userId)
        );
        userRepository.deleteById(userId);
    }
}
