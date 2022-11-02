package com.amaris.web.rest.sco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemSco {
    private String catalogName, itemName, description, email;
    private int id;

    public ItemSco(String catalogName, String itemName, String description, String email, int id) {
        this.catalogName = catalogName;
        this.itemName = itemName;
        this.description = description;
        this.email = email;
        this.id = id;
    }
}
