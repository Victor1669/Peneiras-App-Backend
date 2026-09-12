package peneiras_app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peneiras_app.dto.AddressDTO;
import peneiras_app.dto.GetPlayerResponseDTO;
import peneiras_app.entity.Endereco;
import peneiras_app.entity.Player;
import peneiras_app.repository.PlayerRepository;

import java.util.UUID;

@Service
public class GetPlayerService {

    private final PlayerRepository playerRepository;

    public GetPlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional(readOnly = true)
    public GetPlayerResponseDTO execute(UUID playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player não encontrado"));

        Endereco endereco = player.getAddress();
        AddressDTO addressDTO = null;

        if (endereco != null) {
            addressDTO = new AddressDTO(
                    endereco.getCep(),
                    endereco.getNumero(),
                    endereco.getComplemento()
            );
        }

        return new GetPlayerResponseDTO(
                player.getName(),
                player.getEmail(),
                player.getBirthDate(),
                player.getPosition(),
                player.getDominantFoot(),
                player.getHeightCm(),
                player.getUserImg(),
                addressDTO
        );
    }
}