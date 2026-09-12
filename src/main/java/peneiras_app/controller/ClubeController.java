package peneiras_app.controller;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import peneiras_app.dto.ClubeUpdateDTO;
import peneiras_app.dto.ClubeCreateDTO;
import peneiras_app.dto.GetClubeResponseDTO;
import peneiras_app.dto.MessageResponseDTO;

import peneiras_app.service.EditClubeService;
import peneiras_app.service.CreateClubeService;
import peneiras_app.service.GetClubeService;

@RestController
@RequestMapping("/clubes")
public class ClubeController {

    private final EditClubeService editClubeService;
    private final CreateClubeService clubeService;
    private final GetClubeService getClubeService;

    public ClubeController(
            EditClubeService editClubeService, 
            CreateClubeService clubeService,
            GetClubeService getClubeService
    ) {
        this.editClubeService = editClubeService;
        this.clubeService = clubeService;
        this.getClubeService = getClubeService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> create(@Valid @RequestBody ClubeCreateDTO dto) {
        clubeService.create(dto);
        MessageResponseDTO response = new MessageResponseDTO("Clube criado com sucesso!");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDTO> editClube(
            Authentication authentication,
            @Valid @RequestPart("data") ClubeUpdateDTO dto,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {

        UUID userId = (UUID) authentication.getPrincipal();

        editClubeService.execute(userId, dto, photo);

        MessageResponseDTO response = new MessageResponseDTO("Clube atualizado com sucesso!");

        return ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "/me")
    public ResponseEntity<GetClubeResponseDTO> getPlayerProfile(
            Authentication authentication
    ) {
        UUID userId = (UUID) authentication.getPrincipal();

        GetClubeResponseDTO player = getClubeService.execute(userId);

        return ResponseEntity.ok(player);
    }
}
