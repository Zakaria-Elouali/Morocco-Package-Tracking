package com.morocco.mpt.persistence.users;

import com.morocco.mpt.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserSpringRepository extends JpaRepository<Users, Long> {

    Optional<UserDetails> findByEmail(String email);
    Optional<UserDetails> findByUsername(String email);

    @Query("SELECT u FROM Users  u WHERE u.email = :usernameOrEmail OR u.username = :usernameOrEmail")
    Optional<Users> findByEmailOrUsername(@Param("usernameOrEmail") String usernameOrEmail);



}
