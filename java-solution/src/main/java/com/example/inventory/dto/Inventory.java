package com.example.inventory.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    
    @Singular
    private Set<Item>stocks;
    @Singular
    private Set<Item>lowStocks;
    @Singular
    private Set<Item>anomalies;
}
