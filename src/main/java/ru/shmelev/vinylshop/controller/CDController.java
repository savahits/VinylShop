package ru.shmelev.vinylshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shmelev.vinylshop.DTO.CD.MultipleCDShowDTO;
import ru.shmelev.vinylshop.service.CDService;

import java.util.List;

@RestController
@RequestMapping("api/v1/cd")
public class CDController {

    private final CDService cdService;

    @Autowired
    public CDController(CDService cdService) {
        this.cdService = cdService;
    }

    @GetMapping
    public List<MultipleCDShowDTO> getCDs() {
        return cdService.findAllCD();
    }
}