package com.amaris.repository;

import com.amaris.entity.ItemEntity;
import com.amaris.web.rest.sco.ItemSco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    @Query(value = "SELECT i FROM ItemEntity i " +
            " inner join CatalogEntity c on i.catalogId = c.id " +
            " inner join AccountEntity a on a.id = i.accountId " +
            " WHERE UPPER(COALESCE(i.itemName, '')) LIKE UPPER(CONCAT('%', :itemName, '%'))" +
            " AND UPPER(COALESCE(i.description, '')) LIKE UPPER(CONCAT('%', :description, '%'))" +
            " AND UPPER(COALESCE(c.description, '')) LIKE UPPER(CONCAT('%', :categoryDescription, '%'))" +
            " AND UPPER(COALESCE(c.name, '')) LIKE UPPER(CONCAT('%', :categoryName, '%'))" +
            " AND UPPER(COALESCE(a.email, '')) LIKE UPPER(CONCAT('%', :email, '%'))")
    Page<ItemEntity> findAll(
            @Param("itemName") String itemName,
            @Param("description") String description,
            @Param("categoryDescription")  String categoryDescription,
            @Param("categoryName") String categoryName,
            @Param("email") String email,
            Pageable pageable);

    //Page<ItemEntity> searchItem(ItemSco itemSco);
}
