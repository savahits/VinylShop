package ru.shmelev.vinylshop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.CD.CDShowDTO;
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

    public List<MultipleCDShowDTO> getAllCD() {
        List<Product> products = cdRepository.findAllCD();
        return products.stream().map(cdToDtoMapper::mapToCdShowDTO).collect(Collectors.toList());
    }

    @Transactional
    public CDShowDTO getCDById(Long id) {
        return cdRepository.findById(id).map(cdToDtoMapper::toCdShowDTO).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого cd нет!"));
    }
}
