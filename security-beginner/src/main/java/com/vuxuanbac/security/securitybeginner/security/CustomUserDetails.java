package com.vuxuanbac.security.securitybeginner.security;

import com.vuxuanbac.security.securitybeginner.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user){ this.user = user; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return this.user.getAuthorities();}

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Should declare a field in Account class for indicating the status of the account
        // this.account.getStatus() != AccountStatus.Expired;
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Should declare a field in Account class for indicating the status of the account
        // this.account.getStatus() != AccountStatus.Locked;
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Should declare a field in Account class for indicating the status of the account
        // this.account.getStatus() != AccountStatus.Expired;
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Should declare a field in Account class for indicating the status of the account
        // this.account.getStatus() == AccountStatus.Enabled;
        return true;
    }
}

