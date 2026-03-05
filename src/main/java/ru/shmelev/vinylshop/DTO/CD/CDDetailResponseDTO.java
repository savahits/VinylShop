package ru.shmelev.vinylshop.DTO.CD;

import java.util.List;

public record CDDetailResponseDTO(
        CDShowDTO cd,
        List<MultipleCDShowDTO> otherArtistCDs
) {}

