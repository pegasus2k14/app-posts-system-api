package com.curso.react.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.react.entity.UserEntity;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Long>{
   public UserEntity findByEmail(String email); 
   
   
}
