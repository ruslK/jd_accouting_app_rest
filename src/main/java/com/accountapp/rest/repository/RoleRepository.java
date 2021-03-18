package com.accountapp.rest.repository;

import com.accountapp.rest.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.name in ('Admin')")
    List<Role> getRolesForRoot();

    @Query("select r from Role r where r.name not in ('Root')")
    List<Role> getRoles(String rolesName);
}
