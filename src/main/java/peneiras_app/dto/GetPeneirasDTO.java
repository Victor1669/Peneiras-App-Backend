package peneiras_app.dto;

import peneiras_app.entity.enums.Category;
import peneiras_app.entity.enums.DocumentType;
import peneiras_app.entity.enums.Modality;
import peneiras_app.entity.enums.Uniform;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public class GetPeneirasDTO {

    private final UUID id;
    private final Category category;
    private final Modality modality;
    private final LocalDate date;
    private final LocalTime hour;
    private final Set<Uniform> uniforms;
    private final DocumentType documents;
    private final String about;
    private final String clubeNome;
    private final String clubeImagem;
    private final String endereco;

    public GetPeneirasDTO(
            UUID id,
            Category category,
            Modality modality,
            LocalDate date,
            LocalTime hour,
            Set<Uniform> uniforms,
            DocumentType documents,
            String about,
            String clubeNome,
            String clubeImagem,
            String endereco
    ) {
        this.id = id;
        this.category = category;
        this.modality = modality;
        this.date = date;
        this.hour = hour;
        this.uniforms = uniforms;
        this.documents = documents;
        this.about = about;
        this.clubeNome = clubeNome;
        this.clubeImagem = clubeImagem;
        this.endereco = endereco;
    }

    public UUID getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public Modality getModality() {
        return modality;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public Set<Uniform> getUniforms() {
        return uniforms;
    }

    public DocumentType getDocuments() {
        return documents;
    }

    public String getAbout() {
        return about;
    }

    public String getClubeNome() {
        return clubeNome;
    }

    public String getClubeImagem() {
        return clubeImagem;
    }

    public String getEndereco() {
        return endereco;
    }
}
