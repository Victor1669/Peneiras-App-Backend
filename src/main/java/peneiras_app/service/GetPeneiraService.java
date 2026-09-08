package peneiras_app.service;

import org.springframework.stereotype.Service;

import peneiras_app.entity.enums.Uniform;
import peneiras_app.repository.PeneiraRepository;
import peneiras_app.dto.GetPeneirasDTO;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import peneiras_app.repository.GetPeneiraProjection;

@Service
public class GetPeneiraService {

    private final PeneiraRepository peneiraRepository;
    private final ObjectMapper objectMapper;

    public GetPeneiraService(PeneiraRepository peneiraRepository, ObjectMapper objectMapper) {
        this.peneiraRepository = peneiraRepository;
        this.objectMapper = objectMapper;
    }

    public List<GetPeneirasDTO> getAll() {
        List<GetPeneiraProjection> resultados = peneiraRepository.findAllComClubeEUniformes();

        return resultados.stream()
                .map(item -> {
                    Set<Uniform> uniforms = parseUniforms(item.getUniforms());

                    return new GetPeneirasDTO(
                            item.getId(),
                            item.getCategory(),
                            item.getModality(),
                            item.getDate(),
                            item.getHour(),
                            uniforms,
                            item.getDocuments(),
                            item.getAbout(),
                            item.getClubeNome(),
                            item.getClubeImagem(),
                            item.getEndereco()
                    );
                })
                .toList();
    }

    private Set<Uniform> parseUniforms(String jsonUniforms) {
        if (jsonUniforms == null || jsonUniforms.isBlank()) {
            return Collections.emptySet();
        }
        try {
            List<String> list = objectMapper.readValue(jsonUniforms, new TypeReference<>() {
            });
            return list.stream().map(Uniform::valueOf).collect(Collectors.toSet());
        } catch (JacksonException e) {
            return Collections.emptySet();
        }
    }
}
