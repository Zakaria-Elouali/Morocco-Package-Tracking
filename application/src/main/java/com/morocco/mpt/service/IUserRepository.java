package com.morocco.mpt.service;

import com.morocco.mpt.domain.users.Users;

import java.util.List;

public interface IUserRepository {



    String addUser(Users user, String userName);

    List<Users> allUser();
}
