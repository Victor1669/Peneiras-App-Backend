package peneiras_app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import peneiras_app.entity.enums.Category;

public record ClubeCreateDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,
        @NotBlank(message = "Senha é obrigatória")
        String password,
        @NotNull(message = "Categoria é obrigatória")
        Category category,
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "^\\d{10,11}$", message = "Telefone inválido")
        String phone,
        @Pattern(regexp = "^\\d{10,11}$", message = "WhatsApp inválido")
        String whatsapp,
        String instagramAccount,
        @Valid
        AddressDTO address
        ) {

}
