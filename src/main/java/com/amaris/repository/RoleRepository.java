package com.amaris.repository;

import com.amaris.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    @Query(value = "select * from account\n" +
            "inner join account_role ON account_role.account_id = account.id\n" +
            "inner join role on account_role.role_id = role.id\n" +
            "where account.id = :s", nativeQuery = true)
    List<RoleEntity> findByAccountId(@Param("s") int s);

    default List<RoleEntity> findAllById(Iterable<Integer> ids)
    {
        List<RoleEntity> results = new ArrayList<RoleEntity>();

        for (int id : ids) {
            RoleEntity role = findById(id).orElseThrow(
                    () -> new NotFoundException("Role not found")
            );
            results.add(role);
        }

        if (results.size() == 0)
            throw new NotFoundException("Role not found");

        return results;
    }
}
