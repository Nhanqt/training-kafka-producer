//package com.amaris.repository;
//
//import com.amaris.dto.response.ItemResponseDto;
//import com.amaris.web.rest.sco.ItemSco;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//public class CustomItemRepositoryImpl implements CustomItemRepository {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public List<ItemResponseDto> searchItem(ItemSco searchCondition) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT * FROM item i where i.id = :id");
//        if (searchCondition.getItemId() != 0)
//            sql.append(" AND  like % {id}");
//        this.entityManager.createQuery(sql.toString()).setParameter("id", )
//
//
//    }
//}
