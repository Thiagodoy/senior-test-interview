package com.example.inventory.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.inventory.dto.FileType;
import com.example.inventory.dto.Inventory;
import com.example.inventory.dto.Item;
import com.example.inventory.service.parser.Parser;
import com.example.inventory.service.parser.ParserFactory;
import com.example.inventory.service.parser.mapper.ItemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ParserFactory factory;

    public Inventory parse(MultipartFile file) {
    
            String fileExtension = file.getOriginalFilename().split("\\.")[1].toUpperCase();
    
            Parser<Item> parser = factory.getInstance(FileType.valueOf(fileExtension));
    
            List<Item> itens = parser.parse(file, new ItemMapper());
    
            //Grouping all products by ProductName
           Map<String, List<Item>> productGroupedByNameMap =  itens.stream().collect(Collectors.groupingBy(Item::getProductId));

           Inventory.InventoryBuilder builder = Inventory.builder(); 

            //Summarize stock
            productGroupedByNameMap.values()
            .stream()
            .map(listOfProducts->{
                 return listOfProducts.stream()
                 .sorted(Comparator.comparing(Item::getTimestamp))
                 .reduce(new Item(), Item::merge);   
            }).forEach(item->{
                if(item.getQuantity()< 0){
                    item.setMessage("Stock went negative");
                    builder.anomaly(item);
                }else if(item.getQuantity() >= 0 && item.getQuantity()< 10){
                    builder.lowStock(item);
                }else{
                    builder.stock(item);
                }
            });

        return builder.build();
    }
}
