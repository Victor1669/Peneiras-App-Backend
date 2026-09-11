package peneiras_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import peneiras_app.entity.enums.Category;

public record ClubeUpdateDTO(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        Category category,

        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(
                regexp = "^\\d{10,11}$",
                message = "Telefone inválido"
        )
        String phone,

        @NotBlank(message = "WhatsApp é obrigatório")
        @Pattern(
                regexp = "^\\d{10,11}$",
                message = "WhatsApp inválido"
        )
        String whatsapp,

        String instagramAccount,

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