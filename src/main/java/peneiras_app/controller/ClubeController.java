package peneiras_app.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import peneiras_app.dto.ClubeResponseDTO;
import peneiras_app.dto.ClubeUpdateDTO;
import peneiras_app.service.EditClubeService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/clubes")
public class ClubeController {

    private final EditClubeService editClubeService;

    public ClubeController(EditClubeService editClubeService) {
        this.editClubeService = editClubeService;
    }

    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ClubeResponseDTO> editClube(
            Authentication authentication,
            @Valid @RequestPart("data") ClubeUpdateDTO dto,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {

        UUID userId = (UUID) authentication.getPrincipal();

        ClubeResponseDTO response
                = editClubeService.execute(userId, dto, photo);

        return ResponseEntity.ok(response);
    }
}
