package com.vuxuanbac.authenticator.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "account_email_unique"
                ,columnNames = "email"
        )
})
public class Account {
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
            , cascade = CascadeType.ALL // whenever perform an action on this entity (Account), perform the same action on the associated entity (Role)
    )
    @JoinTable(
            name = "account_role"
            ,joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id") // Account.id
            ,inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") // Role.id
    )
    private Collection<Role> roles;

    public Account(String email, String username, String password, Role... roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = List.of(roles);
    }
}
