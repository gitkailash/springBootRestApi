package com.spring.restapi.restapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.restapi.restapi.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

}
