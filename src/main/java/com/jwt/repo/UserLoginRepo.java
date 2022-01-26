package com.jwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.modal.UserLogin;

@Repository
public interface UserLoginRepo extends JpaRepository<UserLogin, String> {

}
