package com.tw.apistack.base.security.core.respository;

import com.tw.apistack.base.security.core.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    @Modifying
    @Query("update User user set user.password = :password where user.username = :username")
    @Transactional
    void changePassword(@Param("username") String username, @Param("password") String password);
}
