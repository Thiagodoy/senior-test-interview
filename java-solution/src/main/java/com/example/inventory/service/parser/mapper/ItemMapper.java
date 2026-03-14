package com.example.inventory.service.parser.mapper;

import java.util.function.Function;

import org.apache.commons.csv.CSVRecord;

import com.example.inventory.dto.Item;
import com.example.inventory.dto.Item.ItemBuilder;
import com.example.inventory.dto.ItemType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemMapper implements Function<CSVRecord, Item> {

    @Override
    public Item apply(CSVRecord record) {
        ItemBuilder builder = Item.builder();

        try {

            return builder
                    .timestamp(Long.valueOf(record.get("timestamp")))
                    .productId(record.get("product_id"))
                    .productName(record.get("product_name"))
                    .type(ItemType.valueOf(record.get("type").toUpperCase()))
                    .quantity(Long.valueOf(record.get("quantity")))
                    .build();

        } catch (Exception e) {
            log.warn("operation= apply, message=Record {} couldn´t be parsed to Item", record);
            return null;
        }
    }
}
