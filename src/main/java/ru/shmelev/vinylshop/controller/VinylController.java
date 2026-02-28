package ru.shmelev.vinylshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shmelev.vinylshop.DTO.vinyl.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.DTO.vinyl.VinylDetailResponseDTO;
import ru.shmelev.vinylshop.service.VinylService;

import java.util.List;

@RestController
@RequestMapping("api/v1/vinyls")
@Tag(name = "VinylController", description = "Контроллер винилов")
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
            description = "Возвращает всю информацию про данный винил. При include=true добавляет otherArtistVinyls — остальные винилы этого же артиста.")
    public VinylDetailResponseDTO getVinyl(
            @PathVariable Long id,
            @Parameter(description = "Если true — включает список винилов других артистов (otherArtistVinyls)")
            @RequestParam(value = "include", required = false, defaultValue = "false") String include) {
        boolean includeOtherArtistVinyls = "true".equalsIgnoreCase(include);
        return vinylService.getVinylById(id, includeOtherArtistVinyls);
    }

}