package ru.shmelev.vinylshop.DTO.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GenreCreateDTO(
        @Size(max = 100, message = "Название жанра не должно превышать 100 символов")
        @NotBlank(message = "Название жанра не может быть пустым")
        String name
) {
}
