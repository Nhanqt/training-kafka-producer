package com.amaris.service;

import com.amaris.dto.request.CatalogDto;
import com.amaris.dto.request.CreateAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CatalogInterface {
    CatalogDto createCatalog(CatalogDto catalogDto);
    Page<CatalogDto> getAll(Long catalogId, String catalogName, String description);
    List<CatalogDto> getAll();
    CatalogDto updateCatalog(CatalogDto catalogDto);
}
