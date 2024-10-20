package com.ecommerce.controllers.user;

import com.ecommerce.dtos.user.*;
import com.ecommerce.entities.user.UserModel;
import com.ecommerce.services.user.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegisterUserRequestDTO request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO request) {
        return userService.login(request);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public ResponseEntity findByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity forgotPassword(@RequestBody ForgotPasswordRequestDTO request){
        return userService.forgotPassword(request);
    }

    @PatchMapping("/edit-password/{email}")
    public ResponseEntity editPassword(@PathVariable String email, @RequestBody EditPasswordRequestDTO request){
        return userService.editPassword(request, email);
    }

    @PatchMapping("/permission/{email}")
    public ResponseEntity editAdminPermission(@PathVariable String email, @RequestBody UserEditTypeRequestDTO request){
        return userService.editPermission(email, request);
    }

    @PutMapping("/update/{email}")
    public ResponseEntity updateUser(@PathVariable String email, @RequestBody UpdateUserRequestDTO request){
        return userService.updateUser(email, request);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity deleteUser(@PathVariable String email){
        return userService.deleteUser(email);
    }

    @PatchMapping("/active/{email}")
    public ResponseEntity activeUser(@PathVariable String email){
        return userService.activeUser(email);
    }

    @GetMapping("/hello")
    public ResponseEntity<UserModel> helloWorld() {
        UserModel teste = getAuthenticatedUser();
        return ResponseEntity.ok(teste);
    }

    public UserModel getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserModel) authentication.getPrincipal();
    }
}
