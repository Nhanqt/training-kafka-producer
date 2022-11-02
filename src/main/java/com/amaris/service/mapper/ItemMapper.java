package com.amaris.service.mapper;

import com.amaris.dto.request.AccountDto;
import com.amaris.dto.request.CatalogDto;
import com.amaris.dto.request.ItemDto;
import com.amaris.dto.response.ItemResponseDto;
import com.amaris.entity.AccountEntity;
import com.amaris.entity.CatalogEntity;
import com.amaris.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    default ItemEntity toEntity(ItemDto itemDto) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemDto.getId());
        itemEntity.setItemName(itemDto.getItemName());
        itemEntity.setDescription(itemDto.getDescription());
        itemEntity.setNumberof(itemDto.getNumberof());
        CatalogEntity catalog = new CatalogEntity();
        catalog.setId(itemDto.getCatalogId());
        AccountEntity account = new AccountEntity();
        account.setId(itemDto.getAccountId());
        itemEntity.setCatalogId(itemDto.getCatalogId());
        itemEntity.setAccountId(itemDto.getAccountId());
        return itemEntity;
    }
    ItemDto toDto(ItemEntity itemEntity);
    List<ItemDto> getAllItem(List<ItemEntity> itemEntity);
    ItemResponseDto toResponeDto(ItemEntity itemEntity);
}
