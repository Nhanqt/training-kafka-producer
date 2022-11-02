package com.amaris.dto.response;

import com.amaris.dto.request.AccountDto;
import com.amaris.dto.request.CatalogDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {
    private int id;
    private String itemName;
    private String description;
    private CatalogDto catalogDto;
    private AccountDto accountDto;
    private int numberof;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CatalogDto getCatalogDto() {
        return catalogDto;
    }

    public void setCatalogDto(CatalogDto catalogDto) {
        this.catalogDto = catalogDto;
    }

    public AccountDto getAccountDto() {
        return accountDto;
    }

    public void setAccountDto(AccountDto accountDto) {
        this.accountDto = accountDto;
    }

    public int getNumberof() {
        return numberof;
    }

    public void setNumberof(int numberof) {
        this.numberof = numberof;
    }
}
