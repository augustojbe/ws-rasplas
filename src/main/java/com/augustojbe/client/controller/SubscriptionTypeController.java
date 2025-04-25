package com.augustojbe.client.controller;

import com.augustojbe.client.dto.SubscriptionTypeDto;
import com.augustojbe.client.model.jpa.SubscriptionType;
import com.augustojbe.client.service.SubscriptionTypeService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription-type")
public class SubscriptionTypeController {

    private final SubscriptionTypeService subscriptionTypeService;

    public SubscriptionTypeController(SubscriptionTypeService subscriptionTypeService) {
        this.subscriptionTypeService = subscriptionTypeService;
    }


    @GetMapping
    public ResponseEntity<List<SubscriptionType>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionType> findById(@PathVariable("id") Long id){
            return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.findById(id));

    }

    @PostMapping
    public ResponseEntity<SubscriptionType> create(@RequestBody @Valid SubscriptionTypeDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionTypeService.create(dto));

    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionType> update(@PathVariable("id") Long id, @RequestBody SubscriptionTypeDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.update(id, dto));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        subscriptionTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }



}
