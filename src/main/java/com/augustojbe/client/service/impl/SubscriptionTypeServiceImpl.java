package com.augustojbe.client.service.impl;

import com.augustojbe.client.controller.SubscriptionTypeController;
import com.augustojbe.client.dto.SubscriptionTypeDto;
import com.augustojbe.client.exception.BadRequestException;
import com.augustojbe.client.exception.NotFoundException;
import com.augustojbe.client.mapper.SubscriptionTypeMapper;
import com.augustojbe.client.model.jpa.SubscriptionType;
import com.augustojbe.client.repository.jpa.SubscriptionTypeRepository;
import com.augustojbe.client.service.SubscriptionTypeService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private static final String DELETE = "delete";
    private static final String UPDATE = "update";

    private SubscriptionTypeRepository subscriptionTypeRepository;

    public SubscriptionTypeServiceImpl(SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }


    @Override
    @Cacheable(value = "subscriptionType")
    public List<SubscriptionType> findAll() {
        return subscriptionTypeRepository.findAll();
    }

    @Override
    @Cacheable(value = "subscriptionType", key = "#id")
    public SubscriptionType findById(Long id) {
        return getSubscriptionType(id).add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class ).findById(id)).withSelfRel()
        ).add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class ).update(id, new SubscriptionTypeDto())).withRel(UPDATE)
        ).add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class ).delete(id)).withRel(DELETE));
    }

    @Override
    @CacheEvict(value = "subscriptionType", allEntries = true)
    public SubscriptionType create(SubscriptionTypeDto dto) {
        if (Objects.nonNull(dto.getId())){
            throw new BadRequestException("Id deve ser nulo");
        }
        return subscriptionTypeRepository.save(SubscriptionTypeMapper.fromDtoToEntity(dto));
    }

    @Override
    @CacheEvict(value = "subscriptionType", allEntries = true)
    public SubscriptionType update(Long id, SubscriptionTypeDto dto) {
        getSubscriptionType(id);
        dto.setId(id);
        return subscriptionTypeRepository.save(SubscriptionTypeMapper.fromDtoToEntity(dto)).add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class ).findById(id)).withSelfRel()
        ).add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class ).update(id, new SubscriptionTypeDto())).withRel(UPDATE)
        ).add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class ).delete(id)).withRel(DELETE));
    }

    @Override
    @CacheEvict(value = "subscriptionType", allEntries = true)
    public void delete(Long id) {
        getSubscriptionType(id);
        subscriptionTypeRepository.deleteById(id);
    }



    private SubscriptionType getSubscriptionType(Long id) {
        Optional<SubscriptionType> optionalSubscriptionType = subscriptionTypeRepository.findById(id);
        if (optionalSubscriptionType.isEmpty()){
            throw new NotFoundException("SubscriptionType não encontrado");
        }
        return optionalSubscriptionType.get();
    }

}
