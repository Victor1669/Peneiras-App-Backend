package peneiras_app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import peneiras_app.dto.ClubeResponseDTO;
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
    public ClubeResponseDTO execute(UUID userId, ClubeUpdateDTO dto, MultipartFile photo) throws IOException {

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

        // Foto
        if (photo != null && !photo.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(photo);
            clube.setClubeImg(imageUrl);
        }

        // Endereço (só processa se CEP e número forem enviados)
        if (dto.cep() != null && !dto.cep().isBlank()
                && dto.numero() != null && !dto.numero().isBlank()) {

            ViaCepResponseDTO viaCep = viaCepService.buscarCep(dto.cep());
            if (viaCep == null || viaCep.isErro()) {
                throw new RuntimeException("CEP não encontrado ou inválido");
            }

            Endereco endereco = clube.getAddress(); // pega o endereço atual (se existir)

            if (endereco == null) {
                // Não tem endereço ainda → cria um novo
                endereco = new Endereco(
                        viaCep.getLogradouro(),
                        viaCep.getBairro(),
                        dto.numero(),
                        viaCep.getCep(),
                        viaCep.getLocalidade(),
                        viaCep.getUf(),
                        dto.complemento()
                );
            } else {
                // Já tem endereço → atualiza os campos
                viaCep.setLogradouro(viaCep.getLogradouro());
                endereco.setBairro(viaCep.getBairro());
                endereco.setNumero(dto.numero());
                endereco.setCep(viaCep.getCep());
                viaCep.setLocalidade(viaCep.getLocalidade());
                viaCep.setUf(viaCep.getUf());
                endereco.setComplemento(dto.complemento());
            }

            endereco = enderecoRepository.save(endereco);
            clube.setAddress(endereco);
        }

        clubeRepository.save(clube);

        return new ClubeResponseDTO("Clube atualizado com sucesso");
    }
}
