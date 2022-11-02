package com.amaris.repository;

import com.amaris.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    // AccountEntity findByEmail(String email);
    @Query(value = "select r.role_name from account a \n" +
            "inner join account_role ar on ar.account_id = a.id \n" +
            "inner join role r on r.id = ar.role_id \n" +
            "where a.email = :email " , nativeQuery = true)
    List<String> roleName(@Param("email") String email);

    Optional<AccountEntity> findByEmail(String email);
    Optional<AccountEntity> findById(int id);
    @Query(value = "select * from account a where a.email = :email ", nativeQuery = true)
    AccountEntity findAccountByEmail(@Param("email") String email);
    @Query(value = "select a.\"password\" from account a \n" +
            "where a.email = :email " , nativeQuery = true)
    String getPassword(@Param("email") String email);


    @Query(value = "select a.id from account a \n" +
            "where a.email = :email ", nativeQuery = true)
    int getAccountId(@Param("email") String email);
}
