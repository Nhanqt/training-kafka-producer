package com.amaris.service.mapper;

import com.amaris.dto.request.CatalogDto;
import com.amaris.entity.CatalogEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CatalogMapper {
    CatalogEntity toEntity(CatalogDto catalogDto);
    CatalogDto toDto(CatalogEntity catalogEntity);
    List<CatalogDto> toDtoList(List<CatalogEntity> catalogEntities);
}
