package com.amaris.service.impl;

import com.amaris.common.PageResponse;
import com.amaris.config.KafkaProducerConfig;
import com.amaris.dto.request.ItemDto;
import com.amaris.dto.response.ItemResponseDto;
import com.amaris.entity.*;
import com.amaris.exception.exceptions.PageNotFoundException;
import com.amaris.repository.AccountRepository;
import com.amaris.repository.CatalogRepository;
import com.amaris.repository.ItemRepository;
import com.amaris.service.ItemInterface;
import com.amaris.service.mapper.AccountMapper;
import com.amaris.service.mapper.CatalogMapper;
import com.amaris.service.mapper.ItemMapper;
import com.amaris.web.rest.sco.ItemSco;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.webjars.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static com.amaris.common.Constants.*;

@Service
@Transactional(readOnly = true)
public class ItemService implements ItemInterface {
    private final KafkaProducerConfig kafkaProducerConfig;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    final CatalogRepository catalogRepository;
    final AccountRepository accountRepository;
    final AccountMapper accountMapper;
    final CatalogMapper catalogMapper;
    @PersistenceContext
    private EntityManager entityManager;
    public ItemService(KafkaProducerConfig kafkaProducerConfig, ItemRepository itemRepository, ItemMapper itemMapper, CatalogRepository catalogRepository, AccountRepository accountRepository, AccountMapper accountMapper, CatalogMapper catalogMapper) {
        this.kafkaProducerConfig = kafkaProducerConfig;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.catalogRepository = catalogRepository;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.catalogMapper = catalogMapper;
    }

    @Override
    @Transactional
    public ItemDto createItem(ItemDto itemDto) {
        ItemEntity item = itemMapper.toEntity(itemDto);
        ItemEntity itemEntity = itemRepository.save(item);
        return itemMapper.toDto(itemEntity);
    }

    @Override
    public PageResponse<ItemResponseDto> getAll(String itemName ,String description,String categoryName,String email,String categoryDescription, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ItemEntity> itemEntityPage = itemRepository.findAll(itemName,description,categoryName,email,categoryDescription,pageable);
        if(itemEntityPage.isEmpty()){
            throw new PageNotFoundException(PAGE_NOT_FOUND);
        }
        int index = 0;
        List<ItemResponseDto> itemDtoList = new ArrayList<>();
        for (ItemEntity itemEntity : itemEntityPage) {
            itemDtoList.add(itemMapper.toResponeDto(itemEntity));
            CatalogEntity catalogEntity = catalogRepository.findById(itemEntity.getCatalogId()).orElseThrow();
            itemDtoList.get(index).setCatalogDto(catalogMapper.toDto(catalogEntity));
            AccountEntity accountEntity = accountRepository.findById(itemEntity.getAccountId()).orElseThrow();
            itemDtoList.get(index).setAccountDto(accountMapper.toDto(accountEntity));
            index++;
        }
        return new PageResponse<>(itemEntityPage.getNumber(),itemEntityPage.getSize(),itemEntityPage.getTotalElements(),itemEntityPage.getTotalPages()
                ,itemDtoList);
    }
    @Override
    public PageResponse<ItemDto> getAllItemByCatalogName(ItemSco itemSco, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        var qItem = QItemEntity.itemEntity;
        var qCatalog = QCatalogEntity.catalogEntity;
        var qAccount = QAccountEntity.accountEntity;
        var query = new JPAQuery<ItemEntity>(entityManager);
        query.from(qItem).innerJoin(qCatalog).on(qItem.catalogId.eq(qCatalog.id))
                         .innerJoin(qAccount).on(qItem.accountId.eq(qAccount.id));
        if(itemSco.getId() != 0)
            query.where(qItem.id.eq(itemSco.getId()));
        if(StringUtils.hasText(itemSco.getCatalogName()))
             query.where(qCatalog.name.lower().contains(itemSco.getCatalogName().toLowerCase()));
        if(StringUtils.hasText(itemSco.getItemName()))
            query.where(qItem.itemName.lower().contains(itemSco.getItemName().toLowerCase()));
        if(StringUtils.hasText(itemSco.getDescription()))
            query.where(qItem.description.lower().contains(itemSco.getDescription().toLowerCase()));
        if(StringUtils.hasText(itemSco.getEmail()))
            query.where(qAccount.email.lower().contains(itemSco.getEmail().toLowerCase()));
        long totalSize = query.fetchCount();
        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
        List<ItemDto> result = query.select(Projections.fields(ItemDto.class,
                qItem.id,
                qItem.itemName,
                qItem.numberof,
                qItem.description,
                qAccount.email.as("email"),
                qAccount.firstName.as("firstName"),
                qAccount.lastName.as("lastName"),
                qAccount.id.as("accountId"),
                qCatalog.description.as("catalogDescription"),
                qCatalog.name.as("catalogName"),
                qCatalog.id.as("catalogId")))
                .fetch();
        long totalPage = totalSize / pageable.getPageSize() + (totalSize % pageable.getPageSize() == 0 ? 0: 1);
        return new PageResponse<>(pageable.getPageNumber(),pageable.getPageSize(),totalSize,totalPage,result);
    }

    @Override
    public ItemResponseDto findItemById(int id) {
        ItemEntity itemEntity = itemRepository.findById(id).orElseThrow(() -> new NotFoundException(ID_NOT_EXIST));

        kafkaProducerConfig.sendMessage("nhanqt",itemEntity);
        return itemMapper.toResponeDto(itemEntity);
    }

    @Override
    public List<ItemDto> getAll() {
        List<ItemEntity> itemEntities = itemRepository.findAll();
        return itemMapper.getAllItem(itemEntities);
    }
    @Override
    @Transactional
    public ItemDto updateItem(ItemDto itemDto) {
        ItemEntity item = itemMapper.toEntity(itemDto);
        ItemEntity itemEntity = itemRepository.save(item);
        return itemMapper.toDto(itemEntity);
    }
}
