package com.amaris.repository;

import com.amaris.entity.AccountRole;
import com.amaris.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {

    @Query(value = "select r.role_name, r.id from account a \n" +
            "inner join account_role ar on ar.account_id = a.id \n" +
            "inner join \"role\" r on r.id = ar.role_id \n" +
            "where a.id = :id ", nativeQuery = true)
    List<RoleEntity> findAllByAccountId(@Param("id") int id);

    @Query(value = "select ar.account_role_id from account_role ar \n" +
            "where ar.account_id = :accountId and ar.role_id = :roleId ", nativeQuery = true)
    Optional<AccountRole>  findByAccountIdAndRoleId(@Param("accountId") int accountId, @Param("roleId") int roleId);
}
