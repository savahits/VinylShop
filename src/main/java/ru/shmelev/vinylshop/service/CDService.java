package ru.shmelev.vinylshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shmelev.vinylshop.DTO.CD.MultipleCDShowDTO;
import ru.shmelev.vinylshop.domain.Product;
import ru.shmelev.vinylshop.mappers.CDToDtoMapper;
import ru.shmelev.vinylshop.repository.CDRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CDService {

    private final CDRepository cdRepository;
    private final CDToDtoMapper cdToDtoMapper;

    @Autowired
    public CDService(CDRepository cdRepository, CDToDtoMapper cdToDtoMapper) {
        this.cdRepository = cdRepository;
        this.cdToDtoMapper = cdToDtoMapper;
    }

    public List<MultipleCDShowDTO> findAllCD(){
        List<Product> products = cdRepository.findAllCD();
        return products.stream().map(cdToDtoMapper::mapToCDShowDTO).collect(Collectors.toList());
    }
}
