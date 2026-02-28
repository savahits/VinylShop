package ru.shmelev.vinylshop.DTO.vinyl;

import java.util.List;

public record VinylDetailResponseDTO(
        VinylShowDTO vinyl,
        List<MultipleVinylShowDTO> otherArtistVinyls
) {}
