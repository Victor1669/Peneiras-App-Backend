package peneiras_app.controller;

import java.io.IOException;
import java.util.UUID;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import peneiras_app.dto.GetPlayerResponseDTO;

import peneiras_app.dto.PlayerCreateDTO;
import peneiras_app.dto.PlayerUpdateDTO;
import peneiras_app.dto.MessageResponseDTO;

import peneiras_app.service.CreatePlayerService;
import peneiras_app.service.EditPlayerService;
import peneiras_app.service.GetPlayerService;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final CreatePlayerService createPlayerService;
    private final EditPlayerService editPlayerService;
    private final GetPlayerService getPlayerService;

    public PlayerController(
            CreatePlayerService createPlayerService,
            EditPlayerService editPlayerService,
            GetPlayerService getPlayerService
    ) {
        this.createPlayerService = createPlayerService;
        this.editPlayerService = editPlayerService;
        this.getPlayerService = getPlayerService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> create(@Valid @RequestBody PlayerCreateDTO dto) {
        createPlayerService.create(dto);
        MessageResponseDTO response = new MessageResponseDTO("Player criado com sucesso!");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDTO> editPlayer(
            Authentication authentication,
            @Valid @RequestPart("data") PlayerUpdateDTO dto,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {

        UUID userId = (UUID) authentication.getPrincipal();

        editPlayerService.execute(userId, dto, photo);

        MessageResponseDTO response = new MessageResponseDTO("Player atualizado com sucesso");

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<GetPlayerResponseDTO> getPlayerProfile(
            Authentication authentication
    ) {
        UUID userId = (UUID) authentication.getPrincipal();

        GetPlayerResponseDTO player = getPlayerService.execute(userId);

        return ResponseEntity.ok(player);
    }
}
