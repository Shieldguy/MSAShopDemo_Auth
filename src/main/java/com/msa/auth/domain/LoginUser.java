package com.msa.auth.domain;

public interface LoginUser extends SessionUser {
    public  String  getPassword();
    public  Boolean isEnabled();
}
