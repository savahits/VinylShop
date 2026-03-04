package ru.shmelev.vinylshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<MultipleVinylShowDTO>> getVinyls() {
        return ResponseEntity.ok(vinylService.getAllVinyls());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию про конкретный винил",
            description = "Возвращает всю информацию про данный винил. При include=true добавляет otherArtistVinyls — остальные винилы этого же артиста.")
    public ResponseEntity<VinylDetailResponseDTO> getVinyl(
            @PathVariable Long id,
            @Parameter(description = "Если true — включает список винилов других артистов (otherArtistVinyls)")
            @RequestParam(value = "include", required = false, defaultValue = "false") String include) {
        boolean includeOtherArtistVinyls = "true".equalsIgnoreCase(include);
        return ResponseEntity.ok(vinylService.getVinylById(id, includeOtherArtistVinyls));
    }

    @DeleteMapping("/{id}")
    public void deleteVinyl(@PathVariable Long id){
        vinylService.deleteVinylById(id);
    }

}