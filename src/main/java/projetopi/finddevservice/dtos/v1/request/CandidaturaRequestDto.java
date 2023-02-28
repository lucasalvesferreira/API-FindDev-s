package projetopi.finddevservice.dtos.v1.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

public class CandidaturaRequestDto extends RepresentationModel<CandidaturaRequestDto> implements Serializable {

    @Mapping("idCandidatura")
    @JsonProperty("idCandidatura")
    private int key;

    private UUID idDesenvolvedor;

    private int idVaga;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public UUID getIdDesenvolvedor() {
        return idDesenvolvedor;
    }

    public void setIdDesenvolvedor(UUID idDesenvolvedor) {
        this.idDesenvolvedor = idDesenvolvedor;
    }

    public int getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(int idVaga) {
        this.idVaga = idVaga;
    }
}
