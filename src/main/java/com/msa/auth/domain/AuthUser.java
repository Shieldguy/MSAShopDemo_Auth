package com.msa.auth.domain;

import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Builder
public class AuthUser extends EditableDomainData<Long> implements LoginUser {
    @Column(nullable = false, length = 50, unique = true)
    @Setter(value = AccessLevel.PROTECTED)
    private String  username;
    @Column(length = 100)
    @Setter(value = AccessLevel.PROTECTED)
    private String  password;
    @Setter(value = AccessLevel.PROTECTED)
    private Boolean enabled;
    @OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "MAP_AUTHUSER_ROLE",
        joinColumns = @JoinColumn(name = "AUTHUSER_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
    @Setter(value = AccessLevel.PROTECTED)
    private Collection<UserRole> roles;

    @Tolerate
    public AuthUser() {}

    public Long    getId() { return id; }
    public Boolean isEnabled() { return enabled; }
    public String  getUsername() { return username; }
    public String  getPassword() { return password; }
    public Collection<UserRole> getRoles() { return roles; }
}
