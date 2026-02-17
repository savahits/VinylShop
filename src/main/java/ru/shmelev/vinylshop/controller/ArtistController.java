package ru.shmelev.vinylshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shmelev.vinylshop.DTO.ArtistsShowDTO;
import ru.shmelev.vinylshop.domain.Artist;
import ru.shmelev.vinylshop.service.ArtistService;

import java.util.List;

@RestController
@RequestMapping("api/v1/artists")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<ArtistsShowDTO> getArtists() {
        return artistService.getAllArtists();
    }

}
