package ru.shmelev.vinylshop.DTO.CD;

import java.math.BigDecimal;
import java.util.List;

public record MultipleCDShowDTO (
        Long id,
        String title,
        String artistName,
        List<String> genres,
        BigDecimal price
){
}