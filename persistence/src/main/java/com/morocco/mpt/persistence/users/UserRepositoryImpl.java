package com.morocco.mpt.persistence.users;

import com.morocco.mpt.domain.BaseEntity;
import com.morocco.mpt.domain.users.Users;
import com.morocco.mpt.service.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {

    private final UserSpringRepository userSpringRepository;

    @Override
    public Users save(Users user){

            if (user.getId() != null){
                user.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Berlin")));
                user.setStatusCode(BaseEntity.StatusCodes.ACTIVE);
            }else{
                user.setLastModifiedAt(ZonedDateTime.now(ZoneId.of("Europe/Berlin")));
            }
            return userSpringRepository.save(user);
    }

    @Override
    public List<Users> allUser(){
        return userSpringRepository.findAll();
    }

    @Override
    public Users getUser(String username) {
        return (Users) userSpringRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("No user found"));
    }
}
