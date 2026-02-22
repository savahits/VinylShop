package ru.shmelev.vinylshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shmelev.vinylshop.DTO.VinylShowDTO;
import ru.shmelev.vinylshop.service.VinylService;

@RestController
@RequestMapping("api/v1/vinyls")
public class VinylController {

    private final VinylService vinylService;

    @Autowired
    public VinylController(VinylService vinylService) {
        this.vinylService = vinylService;
    }

    @GetMapping("/{id}")
    public VinylShowDTO getVinyl(@PathVariable("id") Long id) {
        return vinylService.getVinylById(id);
    }

}