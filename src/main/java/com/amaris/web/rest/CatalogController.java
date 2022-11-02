package com.amaris.web.rest;


import com.amaris.dto.request.AccountDto;
import com.amaris.dto.request.CatalogDto;
import com.amaris.dto.request.CreateAccountDto;
import com.amaris.service.impl.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping()
    public ResponseEntity<CatalogDto> create(@RequestBody @Valid CatalogDto catalogDto) {
        catalogService.createCatalog(catalogDto);
        return ResponseEntity.ok(catalogDto);
    }

    @PutMapping
    public ResponseEntity<CatalogDto> update(@RequestBody @Valid CatalogDto catalogDto){
        catalogService.updateCatalog(catalogDto);
        return ResponseEntity.ok(catalogDto);
    }

    @GetMapping
    public ResponseEntity<List<CatalogDto>> getAll(){
        List<CatalogDto> catalogDtoList= catalogService.getAll();
        return ResponseEntity.ok(catalogDtoList);
    }
}
