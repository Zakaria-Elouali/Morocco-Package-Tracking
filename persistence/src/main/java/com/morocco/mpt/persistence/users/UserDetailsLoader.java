package com.morocco.mpt.persistence.users;

import com.morocco.mpt.domain.users.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsLoader implements UserDetailsService {

    private final UserSpringRepository userSpringRepository;

    public UserDetailsLoader(UserSpringRepository userSpringRepository){
        this.userSpringRepository =  userSpringRepository ;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return  userSpringRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
