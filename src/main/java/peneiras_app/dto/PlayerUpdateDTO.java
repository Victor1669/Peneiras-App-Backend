package peneiras_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import peneiras_app.entity.enums.DominantFoot;
import peneiras_app.entity.enums.Position;

import java.time.LocalDate;

public record PlayerUpdateDTO(

        String name,

        @Email(message = "E-mail inválido")
        String email,

        LocalDate birthDate,

        Position position,

        DominantFoot dominantFoot,

        Integer heightCm,

        String userImg,

        // Endereço (opcional)
        @Pattern(
                regexp = "^(\\d{8}|\\d{5}-\\d{3})?$",
                message = "CEP inválido"
        )
        String cep,

        @Size(max = 20, message = "Número deve ter no máximo 20 caracteres")
        String numero,

        @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
        String complemento
) {
}