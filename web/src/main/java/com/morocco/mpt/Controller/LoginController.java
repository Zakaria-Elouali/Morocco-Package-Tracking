package com.morocco.mpt.Controller;

import com.morocco.mpt.domain.users.Users;
import com.morocco.mpt.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final UserService userService;

//    @PostMapping("/login")
//    public String login(@RequestBody Users user){
//        System.out.println(user);
//    return userService.verify(user);
//    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) throws IllegalAccessException {
        System.out.println("username:" + user.getUsername() +" password:" + user.getPassword() + "  Email:" + user.getEmail() +" fistName:" + user.getFirstname()+" secondName:" + user.getSecondname());
//        System.out.println("username:" + user.username() +" password:" + user.password() + "  Email:" + user.email() +" fistName:" + user.firstname()+" secondName:" + user.secondname());
        Users response = userService.register(user);
        if (response != null) {
            return new ResponseEntity<>("User added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to Register: " + response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public List<Users> allUser(){
        return userService.allUser();
    }
}
