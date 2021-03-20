package com.accountapp.rest.repository;

import com.accountapp.rest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.role.id = 2")
    List<User> getUsersManagedByRoot();

    @Query("select u from User u " +
            "where u.role.id not IN (1, 2) " +
            "and u.company.id = (select u.company.id from User u where u.id = :adminId)")
    List<User> getUsersManagedByAdmin(Long adminId);
}
