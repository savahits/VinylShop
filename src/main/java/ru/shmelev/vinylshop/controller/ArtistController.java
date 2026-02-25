package ru.shmelev.vinylshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.vinylshop.DTO.artist.ArtistShowDTO;
import ru.shmelev.vinylshop.DTO.artist.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.DTO.vinyl.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.service.ArtistService;
import ru.shmelev.vinylshop.service.VinylService;

import java.util.List;

@RestController
@RequestMapping("api/v1/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final VinylService vinylService;

    @Autowired
    public ArtistController(ArtistService artistService, VinylService vinylService) {
        this.artistService = artistService;
        this.vinylService = vinylService;
    }

    @GetMapping
    @Operation(summary = "Получить всех возможных артистов",
            description = "Возвращает список всех существующих артистов")
    public List<MultipleArtistsShowDTO> getArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("{id}")
    @Operation(summary = "Получить информацию про конкретного артиста",
            description = "Возвращает всю информацию про данного артиста")
    public ArtistShowDTO getArtist(@PathVariable Long id) {
        return artistService.getArtistById(id);
    }

    @GetMapping("{id}/vinyls")
    @Operation(summary = "Получить все винилы данного артиста",
            description = "Возвращает все винилы данного артиста")
    public List<MultipleVinylShowDTO> getVinyls(@PathVariable Long id) {
        return vinylService.getArtistVinyls(id);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить артиста",
            description = "Удаляет данного артиста, ничего не возвращает")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id) {
        artistService.deleteArtistById(id);
    }

}