package com.morocco.mpt.persistence.users;

import com.morocco.mpt.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSpringRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
}
