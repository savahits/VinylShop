package ru.shmelev.vinylshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shmelev.vinylshop.DTO.vinyl.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.DTO.vinyl.VinylShowDTO;
import ru.shmelev.vinylshop.service.VinylService;

import java.util.List;

@RestController
@RequestMapping("api/v1/vinyls")
public class VinylController {

    private final VinylService vinylService;

    @Autowired
    public VinylController(VinylService vinylService) {
        this.vinylService = vinylService;
    }

    @GetMapping
    @Operation(summary = "Получить все возможные винилы",
            description = "Возвращает список всех возможных винилов")
    public List<MultipleVinylShowDTO> getVinyls() {
        return vinylService.getAllVinyls();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию про конкретный винил",
            description = "Возвращает всю информацию про данный винил")
    public VinylShowDTO getVinyl(@PathVariable("id") Long id) {
        return vinylService.getVinylById(id);
    }

}