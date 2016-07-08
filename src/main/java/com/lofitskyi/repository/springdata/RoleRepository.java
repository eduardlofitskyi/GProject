package com.lofitskyi.repository.springdata;

import com.lofitskyi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    String FIND_BY_NAME_SQL = "SELECT r FROM Role r WHERE r.name=:name";

    @Query(FIND_BY_NAME_SQL)
    Role findByName(@Param("name") String name);
}
