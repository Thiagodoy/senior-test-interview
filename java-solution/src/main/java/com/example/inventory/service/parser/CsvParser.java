package com.example.inventory.service.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.inventory.dto.Item;

import lombok.extern.slf4j.Slf4j;

@Component(value = "CSV")
@Slf4j
public class CsvParser implements Parser<Item> {

    @Override
    public List<Item> parse(MultipartFile file, Function<CSVRecord, Item> mapper) {

        try {
            CSVFormat csvFormat = CSVFormat.EXCEL
                    .builder()
                    .setAutoFlush(true)
                    .setIgnoreEmptyLines(true)
                    .setSkipHeaderRecord(true)
                    .setHeader("timestamp", "product_id", "product_name", "type", "quantity")
                    .build();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file.getBytes());
            Reader reader = new InputStreamReader(byteArrayInputStream);

            CSVParser csvParser = csvFormat.parse(reader);

            return csvParser.getRecords().stream().map(mapper).filter(Objects::nonNull).toList();
        } catch (Exception e) {
            log.error("operation=parse, message=Error to parse the file", e);
            throw new RuntimeException("Error to parse the file=" + file.getName(), e);
        }
    }
}
