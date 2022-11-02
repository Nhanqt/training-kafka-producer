package com.amaris.service;


import com.amaris.common.PageResponse;
import com.amaris.dto.request.ItemDto;
import com.amaris.dto.response.ItemResponseDto;
import com.amaris.entity.ItemEntity;
import com.amaris.web.rest.sco.ItemSco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemInterface {
    ItemDto createItem(ItemDto itemDto);
    PageResponse<ItemResponseDto> getAll(String itemName, String description,String categoryName,String email,String categoryDescription, int pageNo, int pageSize);
    List<ItemDto> getAll();
    ItemDto updateItem(ItemDto itemDto);
    PageResponse<ItemDto> getAllItemByCatalogName(ItemSco itemSco, int pageNo, int pageSize);
    ItemResponseDto findItemById(int id);
}
