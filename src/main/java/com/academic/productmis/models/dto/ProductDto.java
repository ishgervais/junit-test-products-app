package com.academic.productmis.models.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductDto {

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;

    public ProductDto(String name, Integer price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

}
