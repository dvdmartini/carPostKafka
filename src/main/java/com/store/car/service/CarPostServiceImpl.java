package com.store.car.service;

import com.store.car.dto.CarPostDto;
import com.store.car.entity.CarPostEntity;
import com.store.car.repository.CarPostRepository;
import com.store.car.repository.OwnerPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarPostServiceImpl implements CarPostService {

    @Autowired
    private CarPostRepository carPostRepository;

    @Autowired
    private OwnerPostRepository ownerPostRepository;


    @Override
    public void newPostDetails(CarPostDto carPostDto) {
        CarPostEntity carPostEntity = mapCarDtoToEntity(carPostDto);
        carPostRepository.save(carPostEntity);
    }

    private CarPostEntity mapCarDtoToEntity(CarPostDto carPostDto) {
        CarPostEntity carPostEntity = new CarPostEntity();

        ownerPostRepository.findById(carPostDto.getOwnerId()).ifPresentOrElse(item -> {
            carPostEntity.setOwnerPost(item);
            carPostEntity.setContact(item.getContactNumber());
        }, () -> {
            throw new RuntimeException();
        });
        carPostEntity.setModel(carPostDto.getModel());
        carPostEntity.setBrand(carPostDto.getBrand());
        carPostEntity.setPrice(carPostDto.getPrice());
        carPostEntity.setCity(carPostDto.getCity());
        carPostEntity.setDescription(carPostDto.getDescription());
        carPostEntity.setEngineVersion(carPostDto.getEngineVersion());
        carPostEntity.setCreatedDate(String.valueOf(new Date()));

        return carPostEntity;
    }

    @Override
    public List<CarPostDto> getCarSales() {
        List<CarPostDto> listCarsSales = new ArrayList<>();
        carPostRepository.findAll().forEach(item -> listCarsSales.add(mapCarEntityToDto(item)));
        return listCarsSales;
    }

    private CarPostDto mapCarEntityToDto(CarPostEntity carPostEntity) {

        return CarPostDto.builder()
                .brand(carPostEntity.getBrand())
                .city(carPostEntity.getCity())
                .model(carPostEntity.getModel())
                .description(carPostEntity.getDescription())
                .engineVersion(carPostEntity.getEngineVersion())
                .createdDate(carPostEntity.getCreatedDate())
                .ownerName(carPostEntity.getOwnerPost().getName())
                .price(carPostEntity.getPrice())
                .build();

    }

    @Override
    public void changeCarSale(CarPostDto carPostDto, Long postId) {
        carPostRepository.findById(postId).ifPresentOrElse(item -> {
            item.setDescription(carPostDto.getDescription());
            item.setContact(carPostDto.getContact());
            item.setPrice(carPostDto.getPrice());
            item.setBrand(carPostDto.getBrand());
            item.setEngineVersion(carPostDto.getEngineVersion());
            item.setModel(carPostDto.getModel());
            carPostRepository.save(item);
        }, () -> {
            throw new NoSuchElementException();
        });

    }

    @Override
    public void removeCarSale(Long postId) {
        carPostRepository.deleteById(postId);

    }
}
