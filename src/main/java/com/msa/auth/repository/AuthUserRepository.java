package com.msa.auth.repository;

import com.msa.auth.domain.AuthUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends CrudRepository<AuthUser, Long> {
    public AuthUser findByUsernameEquals(String username);
}
