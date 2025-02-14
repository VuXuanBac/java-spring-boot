package com.vuxuanbac.authenticator.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountDetails implements UserDetails {
    private Account account;

    public AccountDetails(Account account){
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.account.getRoles().forEach(r -> {
            // append "ROLE_" before role name = an authority
            // so can use in security chain: hasRole(role) = hasAuthority("ROLE_" + role);
            SimpleGrantedAuthority a = new SimpleGrantedAuthority("ROLE_" + r.getRoleName());
            authorities.add(a);
        });
        System.out.println(authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.account.getPassword();
    }

    @Override
    public String getUsername() {
        return this.account.getUsername();
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
