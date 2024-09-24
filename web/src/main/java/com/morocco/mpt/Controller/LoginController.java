package com.morocco.mpt.Controller;

import com.morocco.mpt.domain.users.Users;
import com.morocco.mpt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final UserService userService;

    @PostMapping("/login")
    public Users login(@RequestBody Users user){
    return user;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user, @ModelAttribute("caller") Users caller){
         if (caller == null){caller = new Users("zaka");}
        String response = userService.register(user, caller.username());
        if (response.equals("")) {
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
