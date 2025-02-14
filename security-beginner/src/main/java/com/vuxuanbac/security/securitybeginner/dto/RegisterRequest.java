package com.vuxuanbac.security.securitybeginner.dto;

import com.vuxuanbac.security.securitybeginner.entity.Role;
import com.vuxuanbac.security.securitybeginner.entity.User;
import com.vuxuanbac.security.securitybeginner.repository.RoleRepository;
import com.vuxuanbac.security.securitybeginner.service.RoleService;
import lombok.*;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {
    @Email(message = "The email is invalid")
    private String email;
    @NotEmpty(message = "The username can not be empty")
    private String username;
    @Size(min = 6, message = "The password must be at least 6 characters")
    private String password;

    private Collection<String> roles;
    public static User extract(RegisterRequest info, RoleService roleService){
        User acc = new User();
        acc.setEmail(info.getEmail());
        acc.setUsername(info.getUsername());
        acc.setPassword(info.getPassword());
        Collection<String> roleStr = info.getRoles();
        Collection<Role> roles = new ArrayList<>();
        if (roleStr == null) {
            Role userRole = roleService.getRoleByName("USER");
            roles.add(userRole);
        } else {
            roleStr.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                    case "admin":
                        Role adminRole = roleService.getRoleByName("ADMIN");
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleService.getRoleByName("USER");
                        roles.add(userRole);
                }
            });
        }
        acc.setRoles(roles);
        return acc;
    }
}
