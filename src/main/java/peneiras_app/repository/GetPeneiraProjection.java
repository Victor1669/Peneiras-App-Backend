package peneiras_app.repository;

import java.util.UUID;
import peneiras_app.entity.enums.Category;
import peneiras_app.entity.enums.DocumentType;
import peneiras_app.entity.enums.Modality;
import java.time.LocalDate;
import java.time.LocalTime;

public interface GetPeneiraProjection {

    UUID getId();

    Category getCategory();

    Modality getModality();

    LocalDate getDate();

    LocalTime getHour();

    DocumentType getDocuments();

    String getAbout();

    String getClubeNome();

    String getClubeImagem();

    String getEndereco();

    String getUniforms();
}