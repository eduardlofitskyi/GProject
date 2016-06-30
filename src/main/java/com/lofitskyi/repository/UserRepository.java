package com.lofitskyi.repository;

import com.lofitskyi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query("SELECT u FROM User u WHERE u.login=:login")
    User findByLogin(@Param("login") String login);

    @Query("SELECT u FROM User u WHERE u.email=:email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.id=:id")
    User findById(@Param("id") Long id);
}
