package com.github.semiprojectshop.web.sihu.dto.product.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class AddToCartResponse {
    private Long productId;
    private int currentQuantity;
    private int addedQuantity;
    @JsonIgnore
    private String message;
}
