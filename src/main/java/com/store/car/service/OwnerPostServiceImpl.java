package com.store.car.service;

import com.store.car.dto.OwnerPostDto;
import com.store.car.entity.OwnerPostEntity;
import com.store.car.repository.OwnerPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerPostServiceImpl implements OwnerPostService {

    @Autowired
    private OwnerPostRepository ownerPostRepository;

    @Override
    public void createOwnerPost(OwnerPostDto ownerPostDto) {
        OwnerPostEntity ownerPostEntity = new OwnerPostEntity();

        ownerPostEntity.setName(ownerPostDto.getName());
        ownerPostEntity.setType(ownerPostDto.getType());
        ownerPostEntity.setContactNumber(ownerPostDto.getContactNumber());

        ownerPostRepository.save(ownerPostEntity);
    }
}
