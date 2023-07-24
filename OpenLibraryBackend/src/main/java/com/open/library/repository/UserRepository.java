package com.open.library.repository;

import com.open.library.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "select u.* FROM users as u \n" +
            "join users_roles as ur \n" +
            "on ur.user_id = u.user_id\n" +
            "join (select * from roles where code = 'CUSTOMER') as r \n" +
            "where r.role_id = ur.role_id", nativeQuery = true)
    List<User> findAllUser();
}
