package com.example.inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.inventory.dto.Inventory;
import com.example.inventory.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @PostMapping("/analyze-inventory")
    public ResponseEntity<Inventory> postMethodName(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(service.parse(file));
    }
}
