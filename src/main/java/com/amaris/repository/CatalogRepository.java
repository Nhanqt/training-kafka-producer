package com.amaris.repository;

import com.amaris.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<CatalogEntity, Integer> {

    @Query(value = "select c.* from catalog c\n" +
            "where c.id = :catalogId", nativeQuery = true)
    String findByCatalogId(@Param("catalogId") int catalogId);
}
