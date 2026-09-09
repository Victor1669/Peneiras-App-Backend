package peneiras_app.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import peneiras_app.dto.PlayerResponseDTO;
import peneiras_app.dto.PlayerUpdateDTO;
import peneiras_app.service.EditPlayerService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final EditPlayerService editPlayerService;

    public PlayerController(EditPlayerService editPlayerService) {
        this.editPlayerService = editPlayerService;
    }

    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlayerResponseDTO> editPlayer(
            Authentication authentication,
            @Valid @RequestPart("data") PlayerUpdateDTO dto,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {

        UUID userId = (UUID) authentication.getPrincipal();

        PlayerResponseDTO response
                = editPlayerService.execute(userId, dto, photo);

        return ResponseEntity.ok(response);
    }
}
