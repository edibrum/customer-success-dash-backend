package com.customersu.dashapi.cases.adminSeedData;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminSeedDataController {

    private final AdminSeedDataService adminSeedDataService;

    @PostMapping("/seed-database")
    public ResponseEntity<String> seedDtabase() {
        String mensagem = this.adminSeedDataService.popularSeedDataBase();
        return ResponseEntity.ok(mensagem); //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}