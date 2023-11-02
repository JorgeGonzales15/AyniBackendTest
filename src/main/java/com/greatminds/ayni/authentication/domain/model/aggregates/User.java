package com.greatminds.ayni.authentication.domain.model.aggregates;

import com.greatminds.ayni.authentication.domain.model.valueobjects.EmailAddress;
import com.greatminds.ayni.authentication.domain.model.valueobjects.Password;
import com.greatminds.ayni.authentication.domain.model.valueobjects.Role;
import com.greatminds.ayni.authentication.domain.model.valueobjects.Username;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "users")
public class User extends AbstractAggregateRoot<User> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Embedded
    private Username username;

    @Embedded
    private EmailAddress email;

    @Embedded
    private Password password;

    @Embedded
    private Role role;

    public User(String username, String email, String password, String role){
        this.username = new Username(username);
        this.password = new Password(password);
        this.email = new EmailAddress(email);
        this.role = new Role(role);
    }

    public User() {}


    public String getUsername() { return this.username.username(); }
    public String getEmail() { return this.email.email(); }
    public String getPassword() { return this.password.password(); }
    public Set<Role> getRoles() {
        Set<Role> strRoles = new HashSet<>();
        strRoles.add(this.role);
        return strRoles;
    }
}
