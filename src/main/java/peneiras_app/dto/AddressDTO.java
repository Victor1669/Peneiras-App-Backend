package peneiras_app.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressDTO(
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