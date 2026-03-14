package com.example.inventory.service.parser;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.example.inventory.dto.Item;

public interface Parser<T> {

    List<T> parse(MultipartFile file, Function<CSVRecord, Item> mapper);

}
