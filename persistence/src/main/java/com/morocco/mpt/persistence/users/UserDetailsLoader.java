package com.morocco.mpt.persistence.users;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsLoader implements UserDetailsService {

    private final UserSpringRepository userSpringRepository;

    public UserDetailsLoader(UserSpringRepository userSpringRepository){
        this.userSpringRepository =  userSpringRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        return  userSpringRepository.findByEmailOrUsername(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(usernameOrEmail));
    }
}
