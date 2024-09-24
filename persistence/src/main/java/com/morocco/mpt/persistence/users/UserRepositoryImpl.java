package com.morocco.mpt.persistence.users;

import com.morocco.mpt.domain.BaseEntity;
import com.morocco.mpt.domain.users.Users;
import com.morocco.mpt.service.IUserRepository;
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
    public String addUser(Users user, String userName){
        try{
            if (user != null){
                user.setCreatedBy(userName);
                user.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Berlin")));
                user.setLastModifiedBy(userName);
                user.setLastModifiedAt(ZonedDateTime.now(ZoneId.of("Europe/Berlin")));
                user.setStatusCode(BaseEntity.StatusCodes.ACTIVE);
                userSpringRepository.save(user);
            }
            return "";
        } catch(Exception e){
            return (e.getMessage());
        }
    }

    @Override
    public List<Users> allUser(){
        return userSpringRepository.findAll();
    }
}
