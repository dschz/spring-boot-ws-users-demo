package com.dsc.frm.springboot.controller;

import com.dsc.frm.springboot.dto.UserDto;
import com.dsc.frm.springboot.entity.User;
import com.dsc.frm.springboot.exception.ErrorDetails;
import com.dsc.frm.springboot.exception.ResourceNotFoundException;
import com.dsc.frm.springboot.mapper.UserMapper;
import com.dsc.frm.springboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author DSchneider
 */
@Tag(
        name = "CRUD APIs for user management",
        description = "les operations CRUDs pour gestion des utilisateurs (je le doc en fr)"
)
@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    // create User
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        UserDto userRes = userService.createUser(user);
        return new ResponseEntity<>(userRes, HttpStatus.CREATED);
    }

    // GET a user
    @Operation(
            summary = "Recherche d'un user",
            description = "Recherche d'un utilisateur en base sur son id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Utilisateur bien retrouvé, succès"
    )
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        UserDto userById = userService.getUserById(userId);
        if (userById != null) {
            return new ResponseEntity<>(userById, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    // GET ALL users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> l = userService.getAllUsers();
        return ResponseEntity.ok(l);
    }

    // UPD user
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody UserDto user) {
        user.setId(userId);
        UserDto userUpd = userService.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // DELETE user
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted, id="+userId);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
//        ErrorDetails details = new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                request.getDescription(false),
//                "USER_NOT_FOUND"
//        );
//        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
//    }
}
