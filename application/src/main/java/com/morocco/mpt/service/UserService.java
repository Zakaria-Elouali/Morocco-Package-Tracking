package com.morocco.mpt.service;

import com.morocco.mpt.domain.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final IUserRepository userrepo;

    public String register(Users user, String userName){
        userrepo.addUser(user, userName );
        return "success";
    }

    public String login(Users user){
//        userrepo.login(user);
        return "";
    }

    public List<Users> allUser(){
        List<Users> users = userrepo.allUser();
        return users;
    }
}
