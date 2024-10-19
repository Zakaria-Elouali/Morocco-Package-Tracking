package com.morocco.mpt.domain.users;

import com.morocco.mpt.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static lombok.EqualsAndHashCode.CacheStrategy.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(cacheStrategy = LAZY, callSuper = true)
//@Accessors(fluent = true)
@Entity
@Table(name = "users")
public class Users extends BaseEntity implements UserDetails  {//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String secondname;

    @Column(unique = true, nullable=false) private String username;
    @Column(unique = true) private String email;
    @Column(unique = true) private String phone;
    private Date dateOfBirth;
    private String address;
    private String city;
    private String postal_code;
    private String country;
    @Enumerated(STRING) private Role role;
    public enum Role {SUPERADMIN, ADMIN, USER, VIEWER}

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

//    @Override
//    public String getPassword() {
//        return password;
//    }
//    @Override
//    public String getUsername() {
//        return username;
//    }



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
