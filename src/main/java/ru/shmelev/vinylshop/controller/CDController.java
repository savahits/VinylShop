package ru.shmelev.vinylshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shmelev.vinylshop.DTO.CD.CDShowDTO;
import ru.shmelev.vinylshop.DTO.CD.MultipleCDShowDTO;
import ru.shmelev.vinylshop.service.CDService;

import java.util.List;

@RestController
@RequestMapping("api/v1/cds")
@Tag(name = "CDController", description = "Контроллер CD")
public class CDController {

    private final CDService cdService;

    @Autowired
    public CDController(CDService cdService) {
        this.cdService = cdService;
    }

    @GetMapping
    @Operation(summary = "Получить все возможные CD",
            description = "Возвращает список всех существующих CD")
    public ResponseEntity<List<MultipleCDShowDTO>> getCDs() {
        return ResponseEntity.ok(cdService.getAllCD());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию про конкретный CD",
            description = "Возвращает всю информацию про данный CD")
    public ResponseEntity<CDShowDTO> getCDById(@PathVariable Long id) {
        return ResponseEntity.ok(cdService.getCDById(id));
    }
}