package com.vuxuanbac.security.securitybeginner.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "user_email_unique"
                ,columnNames = "email"
        )
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @ManyToMany(
            fetch = FetchType.EAGER // retrieve roles whenever retrieve an Account
            ,cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "user_role"
            ,joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id") // User.id
            ,inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") // Role.id
    )
    private Collection<Role> roles;

    public User(String email, String username, String password, Role... roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = List.of(roles);
    }
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(r -> {
            // append "ROLE_" before role name = an authority
            // so can use in security chain: hasRole(role) = hasAuthority("ROLE_" + role);
            SimpleGrantedAuthority a = new SimpleGrantedAuthority("ROLE_" + r.getRoleName());
            authorities.add(a);
        });
        System.out.println(authorities);
        return authorities;
    }
}
