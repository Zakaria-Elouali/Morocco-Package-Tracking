package com.morocco.mpt.service.user;

import com.morocco.mpt.domain.users.Users;

import java.util.List;

public interface IUserRepository {



    Users save(Users user);

    List<Users> allUser();

    Users getUser(String username);
}
