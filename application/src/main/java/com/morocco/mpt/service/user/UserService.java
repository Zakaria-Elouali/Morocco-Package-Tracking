package com.morocco.mpt.service.user;

import com.morocco.mpt.domain.users.Users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final IUserRepository userRepository ;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authManager;


    private Users save(Users user){return userRepository.save(user);}

    public Users register(Users user) throws IllegalAccessException {
        String password = (user.getPassword() == null
                || user.getPassword().isEmpty()
                || user.getPassword().trim().isEmpty())
                ? UUID.randomUUID().toString() : user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        user = new Users(null,
                user.getFirstname(),
                user.getSecondname(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getDateOfBirth(),
                user.getAddress(),
                user.getCity(),
                user.getPostal_code(),
                user.getCountry(),
                Users.Role.USER,
                encodedPassword);

        return save(user);
    }



    public List<Users> allUser(){
        List<Users> users = userRepository.allUser();
        return users;
    }

    public Users getUser (String username) {
        return userRepository.getUser(username);
    }





//    public String verify(Users user)  {
//        try{
//            Authentication authentication = authManager.
//                    authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
//            if(authentication.isAuthenticated())
//                return jwtService.generateToken(user.getEmail());
//            return "Failed";
//
//        } catch (Exception e) {
//            return "Failed: " + e.getMessage();
//        }
//
//    }

}
