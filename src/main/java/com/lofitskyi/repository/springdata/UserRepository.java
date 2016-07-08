package com.lofitskyi.repository.springdata;

import com.lofitskyi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String FIND_BY_LOGIN_SQL = "SELECT u FROM User u WHERE u.login=:login";
    String FIND_BY_EMAIL_SQL = "SELECT u FROM User u WHERE u.email=:email";
    String FIND_BY_ID_SQL = "SELECT u FROM User u WHERE u.id=:id";

    @Query(FIND_BY_LOGIN_SQL)
    User findByLogin(@Param("login") String login);

    @Query(FIND_BY_EMAIL_SQL)
    User findByEmail(@Param("email") String email);

    @Query(FIND_BY_ID_SQL)
    User findById(@Param("id") Long id);
}
