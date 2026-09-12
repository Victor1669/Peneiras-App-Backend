package peneiras_app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
        @Valid
        AddressDTO address
        ) {

}
