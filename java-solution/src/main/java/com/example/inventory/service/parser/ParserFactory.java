package com.example.inventory.service.parser;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.inventory.dto.FileType;
import com.example.inventory.dto.Item;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ParserFactory {
    
    private final Map<String, Parser<Item>> parsersMap;

    public Parser<Item> getInstance(FileType type){

         return Optional.ofNullable(parsersMap.get(type.name()))
         .orElseThrow(()-> new RuntimeException("Type not found!"));
    }
}
