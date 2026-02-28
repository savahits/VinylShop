package ru.shmelev.vinylshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.vinylshop.DTO.CD.MultipleCDShowDTO;
import ru.shmelev.vinylshop.DTO.artist.ArtistShowDTO;
import ru.shmelev.vinylshop.DTO.artist.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.DTO.vinyl.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.service.ArtistService;
import ru.shmelev.vinylshop.service.CDService;
import ru.shmelev.vinylshop.service.VinylService;

import java.util.List;

@RestController
@RequestMapping("api/v1/artists")
@Tag(name = "ArtistController", description = "Контроллер артистов")
public class ArtistController {

    private final ArtistService artistService;
    private final VinylService vinylService;
    private final CDService cdService;

    @Autowired
    public ArtistController(ArtistService artistService, VinylService vinylService, CDService cdService) {
        this.artistService = artistService;
        this.vinylService = vinylService;
        this.cdService = cdService;
    }

    @GetMapping
    @Operation(summary = "Получить всех возможных артистов (с пагинацией)",
            description = "Возвращает страницу существующих артистов")
    @Parameters({
            @Parameter(name = "page", description = "Номер страницы", example = "0", in = ParameterIn.QUERY),
            @Parameter(name = "size", description = "Размер страницы", example = "10", in = ParameterIn.QUERY),
            @Parameter(name = "sort", description = "Сортировка в формате: поле,направление. Например: id,asc или nickname,desc",
                    example = "id,asc", in = ParameterIn.QUERY)
    })
    public ResponseEntity<Page<MultipleArtistsShowDTO>> getArtists(
            @PageableDefault(size = 20, sort = "nickname", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(artistService.getAllArtists(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию про конкретного артиста",
            description = "Возвращает всю информацию про данного артиста")
    public ResponseEntity<ArtistShowDTO> getArtist(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @GetMapping("/{id}/vinyls")
    @Operation(summary = "Получить все винилы данного артиста",
            description = "Возвращает все винилы данного артиста")
    public ResponseEntity<List<MultipleVinylShowDTO>> getVinyls(@PathVariable Long id) {
        return ResponseEntity.ok(vinylService.getArtistVinyls(id));
    }

    @GetMapping("/{id}/cd")
    @Operation(summary = "Получить все CD данного артиста",
            description = "Возвращает все CD данного артиста")
    public ResponseEntity<List<MultipleCDShowDTO>> getArtistCD(@PathVariable Long id) {
        return ResponseEntity.ok(cdService.getArtistCD(id));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить артиста",
            description = "Удаляет данного артиста, ничего не возвращает")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtistById(id);
        return ResponseEntity.noContent().build();
    }

}