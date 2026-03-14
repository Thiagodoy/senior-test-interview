package com.example.inventory.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"timestamp","type"})
@JsonInclude(Include.NON_NULL)
public class Item {
    
    private long timestamp;
    @JsonProperty(value="product_id")
    private String productId;
    @JsonProperty(value="product_name")
    private String productName;
    private long quantity;
    private ItemType type;
    private String message;

    public Item merge(Item item) {

        if (Objects.isNull(this.productId) && Objects.nonNull(item.getProductId())) {
            this.productId = item.getProductId();
        }

        if (Objects.isNull(this.productName) && Objects.nonNull(item.getProductName())) {
            this.productName = item.getProductName();
        }

        if (Objects.isNull(this.type) && Objects.nonNull(item.getType())) {
            this.type = item.getType();
        }

        if (ItemType.IN.equals(item.type)) {
            this.quantity += item.getQuantity();
        } else {
            this.quantity -= item.getQuantity();
        }

        return this;
    }

}
