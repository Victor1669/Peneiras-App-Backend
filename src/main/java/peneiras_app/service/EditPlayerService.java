package peneiras_app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import peneiras_app.dto.PlayerUpdateDTO;
import peneiras_app.dto.ViaCepResponseDTO;
import peneiras_app.entity.Endereco;
import peneiras_app.entity.Player;
import peneiras_app.repository.EnderecoRepository;
import peneiras_app.repository.PlayerRepository;

import java.io.IOException;
import java.util.UUID;

@Service
public class EditPlayerService {

    private final PlayerRepository playerRepository;
    private final EnderecoRepository enderecoRepository;
    private final CloudinaryService cloudinaryService;
    private final ViaCepService viaCepService;

    public EditPlayerService(
            PlayerRepository playerRepository,
            EnderecoRepository enderecoRepository,
            CloudinaryService cloudinaryService,
            ViaCepService viaCepService
    ) {
        this.playerRepository = playerRepository;
        this.enderecoRepository = enderecoRepository;
        this.cloudinaryService = cloudinaryService;
        this.viaCepService = viaCepService;
    }

    @Transactional
    public void execute(UUID playerId, PlayerUpdateDTO dto, MultipartFile photo) throws IOException {

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player não encontrado"));

        if (dto.email() != null) {
            if (!player.getEmail().equals(dto.email())
                    && playerRepository.existsByEmail(dto.email())) {
                throw new RuntimeException("E-mail já cadastrado");
            }
            player.setEmail(dto.email());
        }

        if (dto.name() != null) {
            player.setName(dto.name());
        }
        if (dto.birthDate() != null) {
            player.setBirthDate(dto.birthDate());
        }
        if (dto.position() != null) {
            player.setPosition(dto.position());
        }
        if (dto.dominantFoot() != null) {
            player.setDominantFoot(dto.dominantFoot());
        }
        if (dto.heightCm() != null) {
            player.setHeightCm(dto.heightCm());
        }

        if (photo != null && !photo.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(photo);
            player.setUserImg(imageUrl);
        }

        var address = dto.address();

        if (address != null && address.cep() != null && !address.cep().isBlank()
                && address.numero() != null && !address.numero().isBlank()) {

            ViaCepResponseDTO viaCep = viaCepService.buscarCep(address.cep());
            if (viaCep == null || viaCep.isErro()) {
                throw new RuntimeException("CEP não encontrado ou inválido");
            }

            Endereco endereco = player.getAddress();

            if (endereco == null) {
                endereco = new Endereco(
                        viaCep.getLogradouro(),
                        viaCep.getBairro(),
                        address.numero(),
                        viaCep.getCep(),
                        viaCep.getLocalidade(),
                        viaCep.getUf(),
                        address.complemento()
                );
            } else {
                endereco.setRua(viaCep.getLogradouro());
                endereco.setBairro(viaCep.getBairro());
                endereco.setNumero(address.numero());
                endereco.setCep(viaCep.getCep());
                endereco.setCidade(viaCep.getLocalidade());
                endereco.setEstado(viaCep.getUf());
                endereco.setComplemento(address.complemento());
            }

            endereco = enderecoRepository.save(endereco);
            player.setAddress(endereco);
        }

        playerRepository.save(player);
    }
}
