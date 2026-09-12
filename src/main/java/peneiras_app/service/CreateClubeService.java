package peneiras_app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peneiras_app.dto.ClubeCreateDTO;
import peneiras_app.entity.Clube;
import peneiras_app.repository.ClubeRepository;

@Service
public class CreateClubeService {

    private final ClubeRepository clubeRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateClubeService(
            ClubeRepository clubeRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.clubeRepository = clubeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(ClubeCreateDTO dto) {

        if (clubeRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Clube clube = new Clube();

        clube.setName(dto.name());
        clube.setEmail(dto.email());
        clube.setPassword(passwordEncoder.encode(dto.password()));
        clube.setCategory(dto.category());
        clube.setPhone(dto.phone());
        clube.setWhatsapp(dto.whatsapp());
        clube.setInstagramAccount(dto.instagramAccount());

        clubeRepository.save(clube);

    }
}
