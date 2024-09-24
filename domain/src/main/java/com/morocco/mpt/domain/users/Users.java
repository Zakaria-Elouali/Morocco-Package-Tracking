package com.morocco.mpt.domain.users;

import com.morocco.mpt.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;


@Data
@Entity
@Table(name = "Users")
public class Users extends BaseEntity implements UserDetails {


    public Users(String username){
        this.username = username;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String first_name;
    private String second_name;

    @Column(unique = true) private String username;
    @Column(unique = true) private String email;
    @Column(unique = true) private String phone;
    private Date date_of_birth;
    private String address;
    private String city;
    private String postal_code;
    private String country;
    @Enumerated(STRING) private Role role;

    private String password;

    public Users() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String username() {
        return username;
    }

    public enum Role {SUPERADMIN, ADMIN, USER, VIEWER}
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
