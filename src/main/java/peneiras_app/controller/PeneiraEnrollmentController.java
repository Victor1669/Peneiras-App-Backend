package peneiras_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import peneiras_app.dto.PeneiraEnrollmentResponseDTO;
import peneiras_app.service.GetPeneiraEnrollmentService;
import peneiras_app.service.PeneiraEnrollmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/peneiras")
public class PeneiraEnrollmentController {

    private final PeneiraEnrollmentService peneiraEnrollmentService;
    private final GetPeneiraEnrollmentService getPeneiraEnrollmentService;

    public PeneiraEnrollmentController(
            PeneiraEnrollmentService peneiraEnrollmentService,
            GetPeneiraEnrollmentService getPeneiraEnrollmentService
    ) {
        this.peneiraEnrollmentService = peneiraEnrollmentService;
        this.getPeneiraEnrollmentService = getPeneiraEnrollmentService;
    }

    @PostMapping("/{peneiraId}/enroll")
    public ResponseEntity<Void> enroll(
            @PathVariable UUID peneiraId
    ) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        UUID playerId =
                (UUID) authentication.getPrincipal();

        peneiraEnrollmentService.enroll(
                playerId,
                peneiraId
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<PeneiraEnrollmentResponseDTO>> getAll() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        UUID playerId =
                (UUID) authentication.getPrincipal();

        return ResponseEntity.ok(
                getPeneiraEnrollmentService.getAll(playerId)
        );
    }
}