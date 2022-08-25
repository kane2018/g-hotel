package com.kane.hotel.configuration;

import com.kane.hotel.model.Role;
import com.kane.hotel.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        List<Role> roles = user.getRoles();
        for (Role role: roles
             ) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            authorityList.add(authority);
        }

        return authorityList;
    }

    @Override
    public String getPassword() {
        return user.getHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

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

    public String getPicture() {
        return user.getPicture();
    }

    public String getFullName() {
        return this.user.getFullName();
    }
    public User getUser() {
        return user;
    }
}
