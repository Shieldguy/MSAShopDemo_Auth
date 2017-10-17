package com.msa.auth.domain;

import java.util.Collection;

public interface SessionUser {
    public  Long    getId();
    public  String  getUsername();
    public  Collection<UserRole> getRoles();
}
