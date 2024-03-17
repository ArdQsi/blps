package com.webapp.repository;

import com.webapp.model.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
//    UserEntity findByUsername(String username);
//    UserEntity findByIdUser(Long id);
//    void saved(UserEntity user);

    Optional<UserEntity> findUserByEmail(String email);
    UserEntity findUserById(Long id);
}
