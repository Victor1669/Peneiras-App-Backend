package peneiras_app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import peneiras_app.dto.ClubeUpdateDTO;
import peneiras_app.dto.ViaCepResponseDTO;
import peneiras_app.entity.Clube;
import peneiras_app.entity.Endereco;
import peneiras_app.repository.ClubeRepository;
import peneiras_app.repository.EnderecoRepository;

import java.io.IOException;
import java.util.UUID;

@Service
public class EditClubeService {

    private final ClubeRepository clubeRepository;
    private final EnderecoRepository enderecoRepository;
    private final CloudinaryService cloudinaryService;
    private final ViaCepService viaCepService;

    public EditClubeService(
            ClubeRepository clubeRepository,
            EnderecoRepository enderecoRepository,
            CloudinaryService cloudinaryService,
            ViaCepService viaCepService
    ) {
        this.clubeRepository = clubeRepository;
        this.enderecoRepository = enderecoRepository;
        this.cloudinaryService = cloudinaryService;
        this.viaCepService = viaCepService;
    }

    @Transactional
    public void execute(UUID userId, ClubeUpdateDTO dto, MultipartFile photo) throws IOException {

        Clube clube = clubeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Clube não encontrado"));

        if (!clube.getEmail().equals(dto.email())
                && clubeRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        clube.setName(dto.name());
        clube.setEmail(dto.email());
        clube.setCategory(dto.category());
        clube.setPhone(dto.phone());
        clube.setWhatsapp(dto.whatsapp());
        clube.setInstagramAccount(dto.instagramAccount());

        if (photo != null && !photo.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(photo);
            clube.setClubeImg(imageUrl);
        }

        var address = dto.address();

        if (address != null && address.cep() != null && !address.cep().isBlank()
                && address.numero() != null && !address.numero().isBlank()) {

            ViaCepResponseDTO viaCep = viaCepService.buscarCep(address.cep());
            if (viaCep == null || viaCep.isErro()) {
                throw new RuntimeException("CEP não encontrado ou inválido");
            }

            Endereco endereco = clube.getAddress();

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
            clube.setAddress(endereco);
        }

        clubeRepository.save(clube);
    }
}
