package ru.shmelev.vinylshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shmelev.vinylshop.DTO.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.mappers.ArtistToDtoMapper;
import ru.shmelev.vinylshop.repository.ArtistRepository;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistToDtoMapper artistToDtoMapper;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, ArtistToDtoMapper artistToDtoMapper) {
        this.artistRepository = artistRepository;
        this.artistToDtoMapper = artistToDtoMapper;
    }

    public List<MultipleArtistsShowDTO> getAllArtists() {
        return artistToDtoMapper.toShowDTOList(artistRepository.findAll());
    }

}
