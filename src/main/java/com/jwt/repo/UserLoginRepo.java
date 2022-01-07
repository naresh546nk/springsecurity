package com.jwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.modal.UserLogin;

public interface UserLoginRepo extends JpaRepository<UserLogin, String> {

}
