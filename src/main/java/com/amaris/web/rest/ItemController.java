package com.amaris.web.rest;


import com.amaris.common.PageResponse;
import com.amaris.dto.request.ItemDto;
import com.amaris.dto.response.ItemResponseDto;
import com.amaris.entity.ItemEntity;
import com.amaris.service.impl.ItemService;
import com.amaris.web.rest.sco.ItemSco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    private final ItemService itemService;
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    @PostMapping()
    public ResponseEntity<ItemDto> create(@RequestBody @Valid ItemDto itemDto) {
        itemService.createItem(itemDto);
        return ResponseEntity.ok(itemDto);
    }
    @PutMapping
    public ResponseEntity<ItemDto> update(@RequestBody @Valid ItemDto itemDto){
        itemService.updateItem(itemDto);
        return ResponseEntity.ok(itemDto);
    }
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<ItemDto> itemDtoList= itemService.getAll();
        return ResponseEntity.ok(itemDtoList);
    }

    @GetMapping("/id")
    public ResponseEntity<ItemResponseDto> findById(@RequestParam int id){
        ItemResponseDto itemDto = itemService.findItemById(id);
        return ResponseEntity.ok(itemDto);
    }
    @GetMapping("/paging")
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "") int size,
                                    @RequestParam(required = false, defaultValue = "") String itemName,
                                    @RequestParam(required = false, defaultValue = "") String description,
                                    @RequestParam(required = false,defaultValue = "") String categoryDescription,
                                    @RequestParam(required = false,defaultValue = "") String categoryName,
                                    @RequestParam(required = false,defaultValue = "") String email
                                    ){
        PageResponse<ItemResponseDto> itemDtos = itemService.getAll(itemName,description,categoryDescription,categoryName,email, page,size);
        return ResponseEntity.ok(itemDtos);
    }

    @GetMapping("/pagingv2")
    public ResponseEntity<?> getAllPagingV2(@RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "") int size,
                                            @RequestParam(required = false, defaultValue = "0") int id,
                                            @RequestParam(required = false,defaultValue = "") String categoryName,
                                            @RequestParam(required = false,defaultValue = "") String itemName,
                                            @RequestParam(required = false,defaultValue = "") String description,
                                            @RequestParam(required = false,defaultValue = "") String email
    ){
        ItemSco itemSco = new ItemSco(email,categoryName,itemName,description,id);
        PageResponse<ItemDto> itemDtos = itemService.getAllItemByCatalogName(itemSco,page,size);
        return ResponseEntity.ok(itemDtos);
    }
}
