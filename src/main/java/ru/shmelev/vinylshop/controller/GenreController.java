package ru.shmelev.vinylshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.shmelev.vinylshop.DTO.CD.MultipleCDShowDTO;
import ru.shmelev.vinylshop.DTO.genre.GenreCreateDTO;
import ru.shmelev.vinylshop.DTO.genre.GenreResponseDTO;
import ru.shmelev.vinylshop.DTO.artist.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.DTO.vinyl.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.service.ArtistService;
import ru.shmelev.vinylshop.service.CDService;
import ru.shmelev.vinylshop.service.GenreService;
import ru.shmelev.vinylshop.service.VinylService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/genres")
@Tag(name = "GenreController", description = "Контроллер жанров")
public class GenreController {

    private final GenreService genreService;
    private final ArtistService artistService;
    private final VinylService vinylService;
    private final CDService cdService;

    @Autowired
    public GenreController(GenreService genreService, ArtistService artistService, VinylService vinylService, CDService cdService) {
        this.genreService = genreService;
        this.artistService = artistService;
        this.vinylService = vinylService;
        this.cdService = cdService;
    }

    @GetMapping
    @Operation(summary = "Получить все жанры",
            description = "Возвращает список всех доступных музыкальных жанров")
    public List<GenreResponseDTO> getAllGenres() {
        return genreService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать новый жанр",
            description = "Принимает строку, создает новый жанр")
    public ResponseEntity<GenreResponseDTO> createGenre(@Valid @RequestBody GenreCreateDTO dto) {
        GenreResponseDTO created = genreService.createGenre(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить жанр",
            description = "Удаляет один жанр, ничего не возвращает")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Long id) {
        genreService.deleteGenreById(id);
    }


    @GetMapping("/{id}/artists")
    @Operation(summary = "Получить артистов данного жанра (с пагинацией)")
    @Parameters({
            @Parameter(name = "page", description = "Номер страницы", example = "0", in = ParameterIn.QUERY),
            @Parameter(name = "size", description = "Размер страницы", example = "10", in = ParameterIn.QUERY),
            @Parameter(name = "sort", description = "Сортировка в формате: поле,направление. Например: id,asc или nickname,desc",
                    example = "id,asc", in = ParameterIn.QUERY)
    })
    public Page<MultipleArtistsShowDTO> getArtistsByGenre(
            @PathVariable Long id,
            @PageableDefault(size = 20, sort = "nickname", direction = Sort.Direction.ASC)
            Pageable pageable) {

        return artistService.getArtistsByGenreId(id, pageable);
    }

    @GetMapping("/{id}/vinyls")
    @Operation(summary = "Получить винилы данного жанра",
            description = "Возвращает список винила, относящихся к данному жанру")
    public List<MultipleVinylShowDTO> getVinylsByGenre(@PathVariable Long id) {
        return vinylService.getVinylsByGenre(id);
    }

    @GetMapping("/{id}/cd")
    @Operation(summary = "Получить CD данного жанра",
            description = "Возвращает список CD, относящихся к данному жанру")
    public List<MultipleCDShowDTO> getCDByGenre(@PathVariable Long id) {
        return cdService.getCDByGenre(id);
    }


}
